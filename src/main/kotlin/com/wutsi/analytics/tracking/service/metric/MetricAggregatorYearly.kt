package com.wutsi.analytics.tracking.service.metric

import com.wutsi.analytics.tracking.entity.EventType
import com.wutsi.platform.core.storage.StorageService
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter

open class MetricAggregatorYearly(
    eventType: EventType,
    date: LocalDate
) : MetricAggregatorMonthly(eventType, date) {
    override fun getInputUrls(date: LocalDate, storage: StorageService): List<URL> {
        val path = "aggregates/monthly/" + date.format(DateTimeFormatter.ofPattern("yyyy/MM"))
        return collectFilesURLs(path, storage)
    }

    override fun getOutputFilePath(date: LocalDate): String {
        val filepath = date.format(DateTimeFormatter.ofPattern("yyyy"))
        val filename = eventType.name.lowercase() + ".csv"
        return "aggregates/yearly/$filepath/$filename"
    }
}
