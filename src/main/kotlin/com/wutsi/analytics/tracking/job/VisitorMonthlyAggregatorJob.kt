package com.wutsi.analytics.tracking.job

import com.wutsi.analytics.tracking.service.agregator.visit.Visit
import com.wutsi.analytics.tracking.service.agregator.visitor.VisitorMonthlyAggregator
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class VisitorMonthlyAggregatorJob : AbstractMonthlyAggregatorJob<Visit>() {
    override fun getAggregator(date: LocalDate) = VisitorMonthlyAggregator()
    override fun getName() = "visitor"

    @Scheduled(cron = "\${wutsi.application.jobs.monthly-visitor.cron}")
    override fun run() {
        super.run()
    }
}
