package com.wutsi.analytics.tracking.event

import com.wutsi.analytics.tracking.dto.Track

data class TrackPushedPayload(val track: Track = Track())
