package com.wutsi.analytics.tracking.service.metric

import com.opencsv.exceptions.CsvException
import com.wutsi.analytics.tracking.dto.Track
import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.analytics.tracking.service.AbstractDailyAggregator
import com.wutsi.analytics.tracking.service.InputStreamIterator
import com.wutsi.platform.core.storage.StorageService
import java.io.IOException
import java.io.OutputStream
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MetricAggregatorDaily(
    private val metricType: MetricType,
    date: LocalDate
) : AbstractDailyAggregator<Metric>(date) {
    override fun accept(track: Track): Boolean =
        metricType.name.equals(track.event, true) &&
            track.productId != null

    override fun process(track: Track, items: MutableMap<String, Metric>) {
        val key = track.productId.toString()
        if (!items.containsKey(key))
            items[key] = Metric(
                time = track.time,
                tenantId = track.tenantId,
                merchantId = track.merchantId,
                productId = track.productId
            )
        else
            items[key]!!.value++
    }

    @Throws(IOException::class, CsvException::class)
    override fun aggregate(iterator: InputStreamIterator, output: OutputStream): Int {
        val items = loadItems(iterator)
        if (items.isNotEmpty())
            MetricWriter().write(items, output)
        return items.size
    }

    override fun getInputUrls(date: LocalDate, storage: StorageService): List<URL> {
        val urls = mutableListOf<URL>()

        getInputUrls(date, urls, storage)
        getInputUrls(date.plusDays(1), urls, storage)
        return urls
    }

    override fun getOutputFilePath(date: LocalDate): String {
        val filepath = date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        val filename = metricType.name.lowercase() + ".csv"
        return "aggregates/daily/$filepath/$filename"
    }
}
