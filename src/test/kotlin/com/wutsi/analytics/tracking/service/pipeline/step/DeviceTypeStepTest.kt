package com.wutsi.analytics.tracking.service.pipeline.step

import com.wutsi.analytics.tracking.dto.Track
import com.wutsi.analytics.tracking.entity.DeviceType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class DeviceTypeStepTest {
    private val step = DeviceTypeStep()

    @Test
    fun desktop() {
        val track =
            createTrack("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36")
        assertEquals(DeviceType.DESKTOP.name, step.process(track).deviceType)
    }

    @Test
    fun mobile() {
        val track =
            createTrack("Mozilla/5.0 (iPhone; CPU iPhone OS 10_3_1 like Mac OS X) AppleWebKit/603.1.30 (KHTML, like Gecko) Version/11.0 Mobile/14E304 Safari/602.1")
        assertEquals(DeviceType.MOBILE.name, step.process(track).deviceType)
    }

    @Test
    fun tablet() {
        val track =
            createTrack("Mozilla/5.0 (iPad; CPU OS 12_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148")
        assertEquals(DeviceType.MOBILE.name, step.process(track).deviceType)
    }

    @Test
    fun app() {
        val track = createTrack("Dart/2.16 (dart:io)")
        assertEquals(DeviceType.APP.name, step.process(track).deviceType)
    }

    @Test
    fun bot() {
        val track = createTrack("Googlebot/2.1 (+http://www.google.com/bot.html)")
        assertNull(step.process(track).deviceType)
    }

    @Test
    fun noUserAgent() {
        val track = createTrack(null)
        assertNull(step.process(track).deviceType)
    }

    private fun createTrack(ua: String?) = Track(
        ua = ua
    )
}
