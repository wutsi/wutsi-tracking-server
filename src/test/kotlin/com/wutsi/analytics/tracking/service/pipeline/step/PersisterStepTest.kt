package com.wutsi.analytics.tracking.service.pipeline.step

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.wutsi.analytics.tracking.dto.Track
import com.wutsi.analytics.tracking.service.TrackPersister
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PersisterStepTest {
    private lateinit var persister: TrackPersister
    private lateinit var step: PersisterStep

    @BeforeEach
    fun setUp() {
        persister = mock()
        step = PersisterStep(persister, 5)
    }

    @Test
    fun `accumulate event`() {
        step.process(Track())

        verify(persister, never()).persist(any())
    }

    @Test
    fun `flush when buffer-size reached`() {
        step.process(Track())
        step.process(Track())
        step.process(Track())
        step.process(Track())
        step.process(Track())

        val args = argumentCaptor<List<Track>>()
        verify(persister).persist(args.capture())
        kotlin.test.assertEquals(5, args.firstValue.size)
    }

    @Test
    fun `flush on destroy`() {
        step.process(Track())
        step.process(Track())
        step.destroy()

        val args = argumentCaptor<List<Track>>()
        verify(persister).persist(args.capture())
        kotlin.test.assertEquals(2, args.firstValue.size)
    }
}
