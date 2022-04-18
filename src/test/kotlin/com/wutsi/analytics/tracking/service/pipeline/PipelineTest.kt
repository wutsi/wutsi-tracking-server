package com.wutsi.analytics.tracking.service.pipeline

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.wutsi.analytics.tracking.dto.Track
import org.junit.jupiter.api.Test

internal class PipelineTest {

    @Test
    fun process() {
        val track = Track()

        val step1 = mock<Step>()
        doReturn(track).whenever(step1).process(track)

        val step2 = mock<Step>()
        doReturn(track).whenever(step2).process(track)

        val pipeline = Pipeline(listOf(step1, step2))

        pipeline.process(track)

        verify(step1).process(track)
        verify(step2).process(track)
    }
}
