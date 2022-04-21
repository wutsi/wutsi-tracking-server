package com.wutsi.analytics.tracking.service.metric

import com.opencsv.CSVReader
import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.analytics.tracking.service.AbstractMonthlyAggregator
import com.wutsi.platform.core.storage.StorageService
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter

open class MetricAggregatorMonthly(
    protected val metricType: MetricType,
    date: LocalDate
) : AbstractMonthlyAggregator<Metric>(date) {
    override fun getWriter() = MetricWriter()

    override fun aggregate(csv: CSVReader, items: MutableMap<String, Metric>) {
        val iterator: Iterator<Array<String>> = csv.iterator()
        var row = 0
        val mapper = MetricCsvMapper()
        while (iterator.hasNext()) {
            val data = iterator.next()
            if (row == 0) {
                mapper.column(data)
            } else {
                // Load the data
                val item = mapper.map(data)

                // Update the list
                val key = item.productId.toString()
                if (items.containsKey(key))
                    items[key]!!.value += item.value
                else
                    items[key] = item
            }
            row++
        }
    }

    override fun getInputUrls(date: LocalDate, storage: StorageService): List<URL> {
        val path = "aggregates/daily/" + date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        return collectFilesURLs(path, storage)
    }

    override fun getOutputFilePath(date: LocalDate): String {
        val filepath = date.format(DateTimeFormatter.ofPattern("yyyy/MM"))
        val filename = metricType.name.lowercase() + ".csv"
        return "aggregates/monthly/$filepath/$filename"
    }
}
