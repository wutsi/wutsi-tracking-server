package com.wutsi.analytics.tracking.service.metric

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.platform.core.storage.StorageService
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter

open class MetricAggregatorMonthly(
    metricType: MetricType,
    date: LocalDate
) : AbstractMetricAggregatorPeriod(metricType, "MONTHLY", date) {
    override fun getInputUrls(date: LocalDate, storage: StorageService): List<URL> {
        val path = "aggregates/daily/" + date.format(DateTimeFormatter.ofPattern("yyyy/MM"))
        return collectFilesURLs(path, storage)
    }

    override fun getOutputFilePath(date: LocalDate): String {
        val filepath = date.format(DateTimeFormatter.ofPattern("yyyy/MM"))
        val filename = metricType.name.lowercase() + ".csv"
        return "aggregates/monthly/$filepath/$filename"
    }
}
