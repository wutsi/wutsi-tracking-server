package com.wutsi.analytics.tracking.endpoint

import com.nhaarman.mockitokotlin2.verify
import com.wutsi.analytics.tracking.dto.PushTrackRequest
import com.wutsi.analytics.tracking.dto.PushTrackResponse
import com.wutsi.analytics.tracking.dto.Track
import com.wutsi.analytics.tracking.service.pipeline.Pipeline
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PushControllerTest : AbstractEndpointTest() {
    @LocalServerPort
    val port: Int = 0

    @MockBean
    private lateinit var pipeline: Pipeline

    @Test
    fun invoke() {
        // GIVEN
        val url = "http://localhost:$port/v1/tracks"

        // WHEN
        val track = Track()
        val request = PushTrackRequest(track)
        val response = rest.postForEntity(url, request, PushTrackResponse::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)

        verify(pipeline).process(track)
    }
}
