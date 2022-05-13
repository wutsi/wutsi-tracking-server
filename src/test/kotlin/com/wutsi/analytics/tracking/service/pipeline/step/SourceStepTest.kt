package com.wutsi.analytics.tracking.service.pipeline.step

import com.wutsi.analytics.tracking.dto.Track
import com.wutsi.analytics.tracking.entity.Source
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertNull

internal class SourceStepTest {
    private val step = SourceStep()

    @Test
    fun `null`() {
        val track = step.process(createTrack(null))
        assertEquals(Source.DIRECT.name, track.source)
    }

    @Test
    fun empty() {
        val track = step.process(createTrack(""))
        assertEquals(Source.DIRECT.name, track.source)
    }

    @Test
    fun unknown() {
        val track = step.process(createTrack("???"))
        assertNull(track.source)
    }

    @Test
    fun google() {
        val track = step.process(createTrack("https://www.google.ca"))
        assertEquals(Source.SEO.name, track.source)
    }

    @Test
    fun gmail() {
        val track = step.process(createTrack("https://mail.google.com/m/fdkfdkjfkdj"))
        assertEquals(Source.EMAIL.name, track.source)
    }

    @Test
    fun wutsiPixel() {
        val track = step.process(createTrack("https://pixel.mail.wutsi.com/1232"))
        assertEquals(Source.EMAIL.name, track.source)
    }

    @Test
    fun hotmail() {
        val track = step.process(createTrack("https://outlook.live.com/msg/fdfdkjfdkj"))
        assertEquals(Source.EMAIL.name, track.source)
    }

    @Test
    fun facebook() {
        val track = step.process(createTrack("https://www.facebook.com/post/349304903"))
        assertEquals(Source.FACEBOOK.name, track.source)
    }

    @Test
    fun facebookMobile() {
        val track = step.process(createTrack("https://m.facebook.com"))
        assertEquals(Source.FACEBOOK.name, track.source)
    }

    @Test
    fun whatsapp() {
        val track = step.process(createTrack("https://www.whatsapp.com"))
        assertEquals(Source.WHATSAPP.name, track.source)
    }

    @Test
    fun instagram() {
        val track = step.process(createTrack("https://www.instagram.com"))
        assertEquals(Source.INSTAGRAM.name, track.source)
    }

    private fun createTrack(referer: String?) = Track(
        referer = referer
    )
}
