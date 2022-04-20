package com.wutsi.analytics.tracking.job.step

import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter

abstract class AbstractMonthlyAggregatorJob<T> : AbstractAggregatorJob() {
    override fun getOutputFilePath(date: LocalDate): String {
        val filepath = date.format(DateTimeFormatter.ofPattern("yyyy/MM"))
        val filename = getName() + ".csv"
        return "aggregates/monthly/$filepath/$filename"
    }

    override fun getInputUrls(date: LocalDate): List<URL> {
        val path = "aggregates/daily/" + date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        return collectFilesURLs(path)
    }
}
