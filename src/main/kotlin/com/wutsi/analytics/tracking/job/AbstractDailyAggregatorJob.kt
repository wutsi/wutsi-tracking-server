package com.wutsi.analytics.tracking.job

import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter

abstract class AbstractDailyAggregatorJob : AbstractAggregatorJob() {
    override fun getOutputFilePath(date: LocalDate): String {
        val filepath = date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        val filename = getName() + ".csv"
        return "aggregates/daily/$filepath/$filename"
    }

    override fun getInputUrls(date: LocalDate): List<URL> {
        val urls = mutableListOf<URL>()

        getInputUrls(date, urls)
        getInputUrls(date.plusDays(1), urls)
        return urls
    }

    private fun getInputUrls(date: LocalDate, urls: MutableList<URL>) {
        val path = "tracks/" + date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        val found = collectFilesURLs(path)
        urls.addAll(found)
    }
}
