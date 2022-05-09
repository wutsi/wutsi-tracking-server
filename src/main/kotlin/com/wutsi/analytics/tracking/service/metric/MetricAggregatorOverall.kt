package com.wutsi.analytics.tracking.service.metric

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.platform.core.storage.StorageService
import java.net.URL
import java.time.LocalDate

open class MetricAggregatorOverall(
    metricType: MetricType,
    date: LocalDate
) : AbstractMetricAggregatorPeriod(metricType, "YEARLY", date) {
    override fun getInputUrls(date: LocalDate, storage: StorageService): List<URL> =
        collectFilesURLs("aggregates/yearly", storage)

    override fun getOutputFilePath(date: LocalDate): String {
        val filename = metricType.name.lowercase() + ".csv"
        return "aggregates/overall/$filename"
    }
}
