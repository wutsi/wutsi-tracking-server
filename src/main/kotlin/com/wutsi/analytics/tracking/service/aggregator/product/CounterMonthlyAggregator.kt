package com.wutsi.analytics.tracking.service.aggregator.product

import com.opencsv.CSVReader
import com.wutsi.analytics.tracking.service.aggregator.AbstractMonthlyAggregator

open class CounterMonthlyAggregator : AbstractMonthlyAggregator<Counter>() {
    override fun getWriter() = CounterWriter()

    override fun aggregate(csv: CSVReader, items: MutableMap<String, Counter>) {
        val iterator: Iterator<Array<String>> = csv.iterator()
        var row = 0
        while (iterator.hasNext()) {
            val data = iterator.next()
            if (row == 0) {
                // Skip
            } else {
                // Load the data
                val item = Counter()
                item.time = data[0].toLong()
                item.tenantId = data[1]
                item.merchantId = data[2]
                item.productId = data[3]
                item.count = data[4].toLong()

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
