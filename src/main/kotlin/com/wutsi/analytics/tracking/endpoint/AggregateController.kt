package com.wutsi.analytics.tracking.endpoint

import com.wutsi.analytics.tracking.`delegate`.AggregateDelegate
import org.springframework.format.`annotation`.DateTimeFormat
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.`annotation`.GetMapping
import org.springframework.web.bind.`annotation`.RequestParam
import org.springframework.web.bind.`annotation`.RestController
import java.time.LocalDate

@RestController
public class AggregateController(
    private val `delegate`: AggregateDelegate
) {
    @GetMapping("/v1/tracks/aggregate")
    @PreAuthorize(value = "hasAuthority('tracking-manage')")
    public fun invoke(
        @RequestParam(name = "start-date", required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd") startDate: LocalDate
    ) {
        delegate.invoke(startDate)
    }
}
