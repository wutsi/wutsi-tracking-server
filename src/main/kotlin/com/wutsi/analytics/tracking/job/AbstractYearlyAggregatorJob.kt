package com.wutsi.analytics.tracking.job

import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter

abstract class AbstractYearlyAggregatorJob<T> : AbstractAggregatorJob() {
    override fun getOutputFilePath(date: LocalDate): String {
        val filepath = date.format(DateTimeFormatter.ofPattern("yyyy"))
        val filename = getName() + ".csv"
        return "aggregates/yearly/$filepath/$filename"
    }

    override fun getInputUrls(date: LocalDate): List<URL> {
        val path = "aggregates/monthly/" + date.format(DateTimeFormatter.ofPattern("yyyy/MM"))
        return collectFilesURLs(path)
    }
}
