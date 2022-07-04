package com.wutsi.analytics.tracking.endpoint

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import com.wutsi.analytics.tracking.dto.PushTrackResponse
import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.platform.core.storage.StorageService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import java.io.File
import java.net.URL
import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AggregateControllerTest : AbstractEndpointTest() {
    @LocalServerPort
    val port: Int = 0

    @Value("\${wutsi.platform.storage.local.directory:\${user.home}/wutsi/storage}")
    protected lateinit var storageDirectory: String

    @Autowired
    private lateinit var storage: StorageService

    @MockBean
    private lateinit var clock: Clock

    @BeforeEach
    override fun setUp() {
        super.setUp()

        File(storageDirectory).deleteRecursively()

        val now = Instant.ofEpochMilli(1586995200000) // 2020-04-16
        doReturn(now).whenever(clock).instant()
        doReturn(ZoneId.of("UTC")).whenever(clock).zone

        store("/tracks/2020/04/14/2020-04-14-000.csv")
        store("/tracks/2020/04/14/2020-04-14-001.csv")
        store("/tracks/2020/04/15/2020-04-15-000.csv")
    }

    protected fun store(path: String): URL {
        val content = javaClass.getResourceAsStream(path)
        return storage.store(path, content)
    }

    @Test
    fun invoke() {
        val url = "http://localhost:$port/v1/tracks/aggregate?start-date=2020-04-10"
        val response = rest.getForEntity(url, PushTrackResponse::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)

        Thread.sleep(30000)
        MetricType.values().forEach {
            val file = it.name.lowercase() + ".csv"

            assertTrue(File("$storageDirectory/aggregates/daily/2020/04/14/$file").exists(), it.name)
            assertTrue(File("$storageDirectory/aggregates/monthly/2020/04/$file").exists(), it.name)
            assertTrue(File("$storageDirectory/aggregates/yearly/2020/$file").exists(), it.name)
            assertTrue(File("$storageDirectory/aggregates/overall/$file").exists(), it.name)
        }
    }
}
