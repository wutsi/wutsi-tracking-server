package com.wutsi.analytics.tracking.job

import com.wutsi.analytics.tracking.service.agregator.visit.VisitDailyAggregator
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class VisitorDailyAggregatorJob : AbstractDailyAggregatorJob() {
    override fun getAggregator(date: LocalDate) = VisitDailyAggregator(date)
    override fun getName() = "visitor"

    @Scheduled(cron = "\${wutsi.application.jobs.daily-visitor.cron}")
    override fun run() {
        super.run()
    }
}
