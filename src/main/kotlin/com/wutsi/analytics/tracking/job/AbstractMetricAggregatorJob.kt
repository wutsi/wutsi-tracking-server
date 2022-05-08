package com.wutsi.analytics.tracking.job

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.analytics.tracking.service.AbstractAggregator
import com.wutsi.analytics.tracking.service.metric.Metric
import com.wutsi.analytics.tracking.service.metric.MetricAggregatorDaily
import com.wutsi.analytics.tracking.service.metric.MetricAggregatorMonthly
import com.wutsi.analytics.tracking.service.metric.MetricAggregatorOverall
import com.wutsi.analytics.tracking.service.metric.MetricAggregatorYearly
import com.wutsi.platform.core.cron.AbstractCronJob
import com.wutsi.platform.core.logging.DefaultKVLogger
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

        return aggregate(date, MetricAggregatorDaily(getMetricType(), date)) +
            aggregate(date, MetricAggregatorMonthly(getMetricType(), date)) +
            aggregate(date, MetricAggregatorYearly(getMetricType(), date)) +
            aggregate(date, MetricAggregatorOverall(getMetricType(), date))
    }

    private fun aggregate(date: LocalDate, aggregator: AbstractAggregator<Metric>): Long {
        val logger = DefaultKVLogger()
        logger.add("aggregator", aggregator.javaClass.simpleName)
        logger.add("metric_type", getMetricType())
        logger.add("date", date)
        logger.add("input_urls", aggregator.getInputUrls(date, storage))
        logger.add("input_path", aggregator.getOutputFilePath(date))

        try {
            val count = aggregator.aggregate(storage)
            logger.add("count", count)
            logger.add("success", true)

            return count.toLong()
        } catch (ex: Exception) {
            logger.add("success", false)
            logger.setException(ex)
            return 0
        } finally {
            logger.log()
        }
    }
}
