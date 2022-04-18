package com.wutsi.analytics.tracking.job

import com.wutsi.analytics.tracking.service.Importer
import com.wutsi.analytics.tracking.service.aggregator.product.ViewDailyAggregator
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ViewDailyAggregatorJob : AbstractDailyAggregatorJob() {
    override fun getName() = "view"
    override fun getAggregator(date: LocalDate) = ViewDailyAggregator(date)
    override fun getImporter(): Importer? = null

    @Scheduled(cron = "\${wutsi.application.jobs.daily-view.cron}")
    override fun run() {
        super.run()
    }
}
