package com.wutsi.analytics.tracking.service.aggregator.product

import com.opencsv.CSVReader
import com.wutsi.analytics.tracking.service.aggregator.AbstractMonthlyAggregator

open class CounterMonthlyAggregator : AbstractMonthlyAggregator<Counter>() {
    override fun getWriter() = CounterWriter()

    override fun aggregate(csv: CSVReader, items: MutableMap<String, Counter>) {
        val iterator: Iterator<Array<String>> = csv.iterator()
        var row = 0
        val mapper = CounterCsvMapper()
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

    private fun getKey(visit: Counter): String =
        visit.productId.toString()
}
