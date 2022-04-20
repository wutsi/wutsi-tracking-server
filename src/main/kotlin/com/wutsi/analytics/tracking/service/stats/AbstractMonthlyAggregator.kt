package com.wutsi.analytics.tracking.service.stats

import com.opencsv.CSVReader
import com.wutsi.analytics.tracking.service.Aggregator
import com.wutsi.analytics.tracking.service.InputStreamIterator
import java.io.InputStreamReader
import java.io.OutputStream

abstract class AbstractMonthlyAggregator<T> : Aggregator {
    protected abstract fun getWriter(): AbstractWriter<T>
    protected abstract fun aggregate(csv: CSVReader, items: MutableMap<String, T>)

    override fun aggregate(iterator: InputStreamIterator, output: OutputStream): Int {
        val items = mutableMapOf<String, T>()
        while (iterator.hasNext()) {
            val reader = InputStreamReader(iterator.next())
            reader.use {
                val csv = CSVReader(reader)
                csv.use {
                    aggregate(csv, items)
                }
            }
        }
        getWriter().write(items.values.toList(), output)
        return items.size
    }
}
