package com.wutsi.analytics.tracking.service.metric

import com.opencsv.exceptions.CsvException
import com.wutsi.analytics.tracking.dto.Track
import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.analytics.tracking.service.AbstractAggregatorDaily
import com.wutsi.analytics.tracking.service.InputStreamIterator
import com.wutsi.platform.core.logging.KVLogger
import com.wutsi.platform.core.storage.StorageService
import java.io.IOException
import java.io.OutputStream
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MetricAggregatorDaily(
    private val metricType: MetricType,
    date: LocalDate
) : AbstractAggregatorDaily<Metric>(date) {
    override fun accept(track: Track): Boolean =
        metricType.eventType.name.equals(track.event, true) &&
            track.productId != null

    override fun process(track: Track, items: MutableMap<String, Metric>) {
        val key = track.productId.toString()
        if (!items.containsKey(key))
            items[key] = Metric(
                time = track.time,
                tenantId = track.tenantId,
                merchantId = track.merchantId,
                productId = track.productId,
                value = toValue(track)
            )
        else
            items[key]!!.value += toValue(track)
    }

    private fun toValue(track: Track): Long =
        if (metricType == MetricType.SALE)
            track.value?.toLong() ?: 0
        else
            1

    override fun beforeAggregate(storage: StorageService, logger: KVLogger) {
        logger.add("metric", metricType)
        super.beforeAggregate(storage, logger)
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

    override fun acceptInputURL(url: URL): Boolean = true

    override fun getOutputFilePath(date: LocalDate): String {
        val filepath = date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        val filename = metricType.name.lowercase() + ".csv"
        return "aggregates/daily/$filepath/$filename"
    }
}
