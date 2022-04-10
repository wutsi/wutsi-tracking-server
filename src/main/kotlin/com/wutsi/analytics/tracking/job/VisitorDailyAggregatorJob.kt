package com.wutsi.analytics.tracking.job

import com.wutsi.analytics.tracking.service.agregator.visit.VisitAggregator
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class VisitorDailyAggregatorJob : AbstractDailyAggregatorJob() {
    override fun getAggregator(date: LocalDate) = VisitAggregator(date)
    override fun getJobName() = "visitor"

    @Scheduled(cron = "\${wutsi.application.jobs.visitor.cron}")
    override fun run() {
        super.run()
    }
}
