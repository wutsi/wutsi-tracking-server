package com.wutsi.analytics.tracking.endpoint

import com.wutsi.analytics.tracking.`delegate`.PushDelegate
import com.wutsi.analytics.tracking.dto.PushTrackRequest
import com.wutsi.analytics.tracking.dto.PushTrackResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.`annotation`.PostMapping
import org.springframework.web.bind.`annotation`.RequestBody
import org.springframework.web.bind.`annotation`.RestController
import javax.validation.Valid

@RestController
public class PushController(
    private val `delegate`: PushDelegate
) {
    @PostMapping("/v1/tracks")
    @PreAuthorize(value = "hasAuthority('tracking-manage')")
    public fun invoke(@Valid @RequestBody request: PushTrackRequest): PushTrackResponse =
        delegate.invoke(request)
}
