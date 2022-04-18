package com.wutsi.analytics.tracking.service.pipeline

import com.wutsi.analytics.tracking.dto.Track

interface Step {
    fun process(track: Track): Track
}
