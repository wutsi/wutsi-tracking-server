package com.wutsi.analytics.tracking.service.pipeline.step

import com.wutsi.analytics.tracking.dto.Track
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class BotStepTest {
    private val step = BotStep()

    @Test
    fun web() {
        val track = createTrack("Mozilla/5.0 (compatible; MSIE 9.0; Windows Phone OS 7.5; Trident/5.0; IEMobile/9.0)")
        assertFalse(step.process(track).bot)
    }

    @Test
    fun app() {
        val track = createTrack("Dart/2.16 (dart:io)")
        assertFalse(step.process(track).bot)
    }

    @Test
    fun bot() {
        val track = createTrack("Googlebot/2.1 (+http://www.google.com/bot.html)")
        assertTrue(step.process(track).bot)
    }

    @Test
    fun noUserAgent() {
        val track = createTrack(null)
        assertFalse(step.process(track).bot)
    }

    private fun createTrack(ua: String?) = Track(
        ua = ua,
        bot = false
    )
}
