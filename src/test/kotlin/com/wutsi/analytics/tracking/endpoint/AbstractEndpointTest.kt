package com.wutsi.analytics.tracking.endpoint

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import com.wutsi.platform.core.security.SubjectType
import com.wutsi.platform.core.security.SubjectType.USER
import com.wutsi.platform.core.security.spring.SpringAuthorizationRequestInterceptor
import com.wutsi.platform.core.security.spring.jwt.JWTBuilder
import com.wutsi.platform.core.test.TestRSAKeyProvider
import com.wutsi.platform.core.test.TestTokenProvider
import com.wutsi.platform.core.tracing.TracingContext
import com.wutsi.platform.core.tracing.spring.SpringTracingRequestInterceptor
import feign.FeignException
import feign.Request
import feign.RequestTemplate
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.web.client.RestTemplate
import java.nio.charset.Charset
import java.util.UUID

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AbstractEndpointTest {
    companion object {
        const val DEVICE_ID = "0000-1111"
        const val ACCOUNT_ID = 1L
        const val ACCOUNT_NAME = "Ray Sponsible"
        const val TENANT_ID = "1"
    }

    @MockBean
    private lateinit var tracingContext: TracingContext

    protected lateinit var rest: RestTemplate

    lateinit var traceId: String

    @BeforeEach
    fun setUp() {
        traceId = UUID.randomUUID().toString()
        doReturn(DEVICE_ID).whenever(tracingContext).deviceId()
        doReturn(traceId).whenever(tracingContext).traceId()
        doReturn(TENANT_ID).whenever(tracingContext).tenantId()

        rest = createResTemplate()
    }

    private fun createResTemplate(
        scope: List<String> = listOf(
            "tracking-manage",
        ),
        subjectId: Long = ACCOUNT_ID,
        subjectType: SubjectType = USER
    ): RestTemplate {
        val rest = RestTemplate()
        val tokenProvider = TestTokenProvider(
            JWTBuilder(
                subject = subjectId.toString(),
                name = ACCOUNT_NAME,
                subjectType = subjectType,
                scope = scope,
                keyProvider = TestRSAKeyProvider(),
                admin = false
            ).build()
        )

        rest.interceptors.add(SpringTracingRequestInterceptor(tracingContext))
        rest.interceptors.add(SpringAuthorizationRequestInterceptor(tokenProvider))
        return rest
    }

    protected fun createFeignException(errorCode: String) = FeignException.Conflict(
        "",
        Request.create(
            Request.HttpMethod.POST,
            "https://www.google.ca",
            emptyMap(),
            "".toByteArray(),
            Charset.defaultCharset(),
            RequestTemplate()
        ),
        """
            {
                "error":{
                    "code": "$errorCode"
                }
            }
        """.trimIndent().toByteArray(),
        emptyMap()
    )
}
