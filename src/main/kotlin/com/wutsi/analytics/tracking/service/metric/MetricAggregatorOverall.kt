package com.wutsi.analytics.tracking.service.metric

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.platform.core.storage.StorageService
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter

open class MetricAggregatorOverall(
    metricType: MetricType,
    date: LocalDate
) : MetricAggregatorMonthly(metricType, date) {
    override fun getInputUrls(date: LocalDate, storage: StorageService): List<URL> {
        val path = "aggregates/yearly/" + date.format(DateTimeFormatter.ofPattern("yyyy"))
        return collectFilesURLs(path, storage)
    }

    override fun getOutputFilePath(date: LocalDate): String {
        val filename = metricType.name.lowercase() + ".csv"
        return "aggregates/overall/$filename"
    }
}
