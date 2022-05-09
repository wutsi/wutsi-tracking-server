package com.wutsi.analytics.tracking.service

import com.opencsv.CSVReader
import com.wutsi.platform.core.logging.KVLogger
import com.wutsi.platform.core.storage.StorageService
import java.io.InputStreamReader
import java.io.OutputStream
import java.time.LocalDate

abstract class AbstractAggregatorPeriod<T>(val date: LocalDate, private val period: String) :
    AbstractAggregator<T>(date) {
    protected abstract fun getWriter(): AbstractWriter<T>
    protected abstract fun aggregate(csv: CSVReader, items: MutableMap<String, T>)

    override fun beforeAggregate(storage: StorageService, logger: KVLogger) {
        logger.add("period", period)
        super.beforeAggregate(storage, logger)
    }

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
