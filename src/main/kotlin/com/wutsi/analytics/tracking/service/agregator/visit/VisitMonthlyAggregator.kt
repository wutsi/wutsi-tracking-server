package com.wutsi.analytics.tracking.service.agregator.visit

import com.opencsv.CSVReader
import com.wutsi.analytics.tracking.service.agregator.AbstractMonthlyAggregator

open class VisitMonthlyAggregator : AbstractMonthlyAggregator<Visit>() {
    override fun getWriter() = VisitWriter()

    override fun aggregate(csv: CSVReader, items: MutableMap<String, Visit>) {
        val iterator: Iterator<Array<String>> = csv.iterator()
        var row = 0
        while (iterator.hasNext()) {
            val data = iterator.next()
            if (row == 0) {
                // Skip
            } else {
                // Load the data
                val item = Visit()
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

    private fun getKey(visit: Visit): String =
        visit.productId.toString()
}
