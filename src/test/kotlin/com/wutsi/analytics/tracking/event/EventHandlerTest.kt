package com.wutsi.analytics.tracking.event

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.verify
import com.wutsi.analytics.tracking.delegate.PushDelegate
import com.wutsi.analytics.tracking.dto.PushTrackRequest
import com.wutsi.analytics.tracking.dto.Track
import com.wutsi.analytics.tracking.endpoint.AbstractEndpointTest
import com.wutsi.platform.core.stream.Event
import com.wutsi.platform.core.stream.EventTracingData
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.OffsetDateTime
import java.util.UUID

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class EventHandlerTest : AbstractEndpointTest() {
    @MockBean
    private lateinit var delegate: PushDelegate

    @Autowired
    private lateinit var handler: EventHandler

    @Test
    fun onEvent() {
        val track = Track()
        val event = createEvent(EventURN.TRACK_PUSHED.urn, track)

        handler.onEvent(event)

        verify(delegate).invoke(PushTrackRequest(track))
    }

    private fun createEvent(
        eventType: String,
        track: Track
    ): Event =
        Event(
            id = UUID.randomUUID().toString(),
            type = eventType,
            timestamp = OffsetDateTime.now(),
            tracingData = EventTracingData(
                tenantId = TENANT_ID.toString(),
                traceId = UUID.randomUUID().toString(),
                clientId = "-",
                clientInfo = "--",
                deviceId = DEVICE_ID
            ),
            payload = ObjectMapper().writeValueAsString(
                TrackPushedPayload(track)
            )
        )
}
