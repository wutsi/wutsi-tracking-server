package com.wutsi.analytics.tracking.service.stats.metric

import com.opencsv.CSVReader
import com.wutsi.analytics.tracking.service.stats.AbstractMonthlyAggregator

open class MetricMonthlyAggregator : AbstractMonthlyAggregator<Metric>() {
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
                val key = getKey(item)
                if (items.containsKey(key))
                    items[key]!!.count += item.count
                else
                    items[key] = item
            }
            row++
        }
    }

    private fun getKey(visit: Metric): String =
        visit.productId.toString()
}
