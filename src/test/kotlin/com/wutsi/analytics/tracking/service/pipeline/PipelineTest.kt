package com.wutsi.analytics.tracking.service.pipeline

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.wutsi.analytics.tracking.dto.Track
import org.junit.jupiter.api.Test

internal class PipelineTest {

    @Test
    fun process() {
        val step1 = mock<Step>()
        val step2 = mock<Step>()
        val pipeline = Pipeline(listOf(step1, step2))

        val track = Track()
        pipeline.process(track)

        verify(step1).process(track)
        verify(step2).process(track)
    }
}
