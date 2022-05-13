package com.wutsi.analytics.tracking.service.pipeline

import com.wutsi.analytics.tracking.dto.Track

open class Pipeline(val steps: List<Step>) : Step {
    override fun process(track: Track): Track {
        var cur = track
        steps.forEach {
            cur = it.process(cur)
        }
        return cur
    }
}
