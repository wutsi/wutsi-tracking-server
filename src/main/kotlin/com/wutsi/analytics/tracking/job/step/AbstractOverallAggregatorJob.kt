package com.wutsi.analytics.tracking.job.step

import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter

abstract class AbstractOverallAggregatorJob<T> : AbstractAggregatorJob() {
    override fun getOutputFilePath(date: LocalDate): String {
        val filename = getName() + ".csv"
        return "aggregates/overall/$filename"
    }

    override fun getInputUrls(date: LocalDate): List<URL> {
        val path = "aggregates/yearly/" + date.format(DateTimeFormatter.ofPattern("yyyy"))
        return collectFilesURLs(path)
    }
}
