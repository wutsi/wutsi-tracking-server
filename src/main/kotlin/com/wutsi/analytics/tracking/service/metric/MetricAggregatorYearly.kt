package com.wutsi.analytics.tracking.service.metric

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.platform.core.storage.StorageService
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter

open class MetricAggregatorYearly(
    metricType: MetricType,
    date: LocalDate
) : AbstractMetricAggregatorPeriod(metricType, "YEARLY", date) {
    override fun getInputUrls(date: LocalDate, storage: StorageService): List<URL> {
        val path = "aggregates/monthly/" + date.format(DateTimeFormatter.ofPattern("yyyy"))
        return collectFilesURLs(path, storage)
    }

    override fun acceptInputURL(url: URL): Boolean =
        url.file.endsWith(metricType.name.lowercase() + ".csv")

    override fun getOutputFilePath(date: LocalDate): String {
        val filepath = date.format(DateTimeFormatter.ofPattern("yyyy"))
        val filename = metricType.name.lowercase() + ".csv"
        return "aggregates/yearly/$filepath/$filename"
    }
}
