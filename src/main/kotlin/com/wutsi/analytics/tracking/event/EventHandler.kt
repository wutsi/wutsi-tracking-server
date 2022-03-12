package com.wutsi.analytics.tracking.event

import com.fasterxml.jackson.databind.ObjectMapper
import com.wutsi.analytics.tracking.delegate.PushDelegate
import com.wutsi.analytics.tracking.dto.PushTrackRequest
import com.wutsi.platform.core.logging.DefaultKVLogger
import com.wutsi.platform.core.stream.Event
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class EventHandler(
    private val objectMapper: ObjectMapper,
    private val delegate: PushDelegate,
) {
    @EventListener
    fun onEvent(event: Event) {
        if (EventURN.TRACK_PUSHED.urn == event.type) {
            val payload = objectMapper.readValue(event.payload, TrackPushedPayload::class.java)
            onTrackPushed(payload)
        }
    }

    private fun onTrackPushed(payload: TrackPushedPayload) {
        val track = payload.track
        val logger = DefaultKVLogger()
        logger.add("correlation_id", track.correlationId)
        logger.add("account_id", track.accountId)
        logger.add("merchant_id", track.merchantId)
        logger.add("product_id", track.productId)
        logger.add("tenant_id", track.tenantId)
        logger.add("page", track.page)
        logger.add("event", track.event)
        logger.add("value", track.value)

        try {
            delegate.invoke(
                PushTrackRequest(track = track)
            )
        } catch (ex: Exception) {
            logger.setException(ex)
        } finally {
            logger.log()
        }
    }
}
