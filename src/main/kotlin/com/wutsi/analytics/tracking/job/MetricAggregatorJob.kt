package com.wutsi.analytics.tracking.job

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.analytics.tracking.service.AbstractAggregator
import com.wutsi.analytics.tracking.service.metric.Metric
import com.wutsi.analytics.tracking.service.metric.MetricAggregatorDaily
import com.wutsi.analytics.tracking.service.metric.MetricAggregatorMonthly
import com.wutsi.analytics.tracking.service.metric.MetricAggregatorOverall
import com.wutsi.analytics.tracking.service.metric.MetricAggregatorYearly
import com.wutsi.platform.core.cron.AbstractCronJob
import com.wutsi.platform.core.storage.StorageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.LocalDate

@Service
class MetricAggregatorJob : AbstractCronJob() {
    @Autowired
    protected lateinit var storage: StorageService

    @Autowired
    protected lateinit var clock: Clock

    override fun getToken(): String? = null

    override fun getJobName(): String = "metric"

    @Scheduled(cron = "\${wutsi.application.jobs.metric.cron}")
    override fun run() {
        super.run()
    }

    override fun doRun(): Long {
        val date = LocalDate.now(clock).minusDays(1) // Yesterday
        var count = 0L
        MetricType.values().forEach {
            count += doRun(date, it)
        }
        return count
    }

    private fun doRun(date: LocalDate, type: MetricType): Long {
        return aggregate(MetricAggregatorDaily(type, date)) +
            aggregate(MetricAggregatorMonthly(type, date)) +
            aggregate(MetricAggregatorYearly(type, date)) +
            aggregate(MetricAggregatorOverall(type, date))
    }

    private fun aggregate(aggregator: AbstractAggregator<Metric>): Long =
        aggregator.aggregate(storage).toLong()
}
