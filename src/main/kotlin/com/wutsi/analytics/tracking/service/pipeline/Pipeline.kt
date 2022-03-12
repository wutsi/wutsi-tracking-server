package com.wutsi.analytics.tracking.service.pipeline

import com.wutsi.analytics.tracking.dto.Track

open class Pipeline(private val steps: List<Step>) : Step {
    override fun process(track: Track) {
        steps.forEach { it.process(track) }
    }
}
