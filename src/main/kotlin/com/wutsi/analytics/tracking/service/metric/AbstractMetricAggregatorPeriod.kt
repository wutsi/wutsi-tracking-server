package com.wutsi.analytics.tracking.service.metric

import com.opencsv.CSVReader
import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.analytics.tracking.service.AbstractAggregatorPeriod
import com.wutsi.platform.core.logging.KVLogger
import com.wutsi.platform.core.storage.StorageService
import java.net.URL
import java.time.LocalDate

abstract class AbstractMetricAggregatorPeriod(
    protected val metricType: MetricType,
    period: String,
    date: LocalDate
) : AbstractAggregatorPeriod<Metric>(date, period) {
    override fun getWriter() = MetricWriter()

    override fun beforeAggregate(storage: StorageService, logger: KVLogger) {
        logger.add("metric", metricType)
        super.beforeAggregate(storage, logger)
    }

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

    override fun acceptInputURL(url: URL): Boolean =
        url.file.endsWith(metricType.name.lowercase() + ".csv")
}
