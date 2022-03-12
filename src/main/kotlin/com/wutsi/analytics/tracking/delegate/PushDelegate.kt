package com.wutsi.analytics.tracking.`delegate`

import com.wutsi.analytics.tracking.dto.PushTrackRequest
import com.wutsi.analytics.tracking.dto.PushTrackResponse
import com.wutsi.analytics.tracking.service.pipeline.Pipeline
import org.springframework.stereotype.Service
import java.util.UUID

@Service
public class PushDelegate(
    private val pipeline: Pipeline,
) {
    public fun invoke(request: PushTrackRequest): PushTrackResponse {
        pipeline.process(request.track)
        return PushTrackResponse(
            transactionId = UUID.randomUUID().toString()
        )
    }
}
