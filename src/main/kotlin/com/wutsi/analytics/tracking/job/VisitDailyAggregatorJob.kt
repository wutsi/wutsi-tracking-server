package com.wutsi.analytics.tracking.job

import com.wutsi.analytics.tracking.service.agregator.visit.VisitDailyAggregator
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class VisitDailyAggregatorJob : AbstractDailyAggregatorJob() {
    override fun getAggregator(date: LocalDate) = VisitDailyAggregator(date)
    override fun getName() = "visit"

    @Scheduled(cron = "\${wutsi.application.jobs.daily-visit.cron}")
    override fun run() {
        super.run()
    }
}
