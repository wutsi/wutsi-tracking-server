package com.wutsi.analytics.tracking.job

import com.wutsi.analytics.tracking.service.agregator.visit.Visit
import com.wutsi.analytics.tracking.service.agregator.visit.VisitMonthlyAggregator
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class VisitMonthlyAggregatorJob : AbstractMonthlyAggregatorJob<Visit>() {
    override fun getAggregator(date: LocalDate) = VisitMonthlyAggregator()
    override fun getName() = "visit"

    @Scheduled(cron = "\${wutsi.application.jobs.monthly-visit.cron}")
    override fun run() {
        super.run()
    }
}
