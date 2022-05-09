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
import java.time.Clock
import java.time.LocalDate

abstract class AbstractMetricAggregatorJob : AbstractCronJob() {
    @Autowired
    protected lateinit var storage: StorageService

    @Autowired
    protected lateinit var clock: Clock

    protected abstract fun getMetricType(): MetricType

    override fun getToken(): String? = null

    override fun doRun(): Long {
        val date = LocalDate.now(clock).minusDays(1) // Yesterday
        val type = getMetricType()
        return aggregate(MetricAggregatorDaily(type, date)) +
            aggregate(MetricAggregatorMonthly(type, date)) +
            aggregate(MetricAggregatorYearly(type, date)) +
            aggregate(MetricAggregatorOverall(type, date))
    }

    private fun aggregate(aggregator: AbstractAggregator<Metric>): Long =
        aggregator.aggregate(storage).toLong()
}
