package com.wutsi.analytics.tracking.service

import com.opencsv.CSVReader
import com.wutsi.analytics.tracking.dto.Track
import com.wutsi.platform.core.logging.KVLogger
import com.wutsi.platform.core.storage.StorageService
import java.io.InputStreamReader
import java.io.Reader
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

abstract class AbstractAggregatorDaily<T>(private val date: LocalDate) : AbstractAggregator<T>(date) {
    protected abstract fun accept(track: Track): Boolean
    protected abstract fun process(track: Track, items: MutableMap<String, T>)

    override fun beforeAggregate(storage: StorageService, logger: KVLogger) {
        logger.add("period", "DAILY")
        super.beforeAggregate(storage, logger)
    }

    protected open fun loadItems(iterator: InputStreamIterator): List<T> {
        val items = mutableMapOf<String, T>()
        while (iterator.hasNext()) {
            val reader = InputStreamReader(iterator.next())
            reader.use {
                loadItems(it, items)
            }
        }
        return items.values.toList()
    }

    protected open fun loadItems(reader: Reader, items: MutableMap<String, T>) {
        val mapper = TrackCsvMapper()
        val csv = CSVReader(reader)
        csv.use {
            val iterator: Iterator<Array<String>> = csv.iterator()
            var row = 0
            while (iterator.hasNext()) {
                if (row == 0) {
                    mapper.column(iterator.next())
                } else {
                    val track: Track = mapper.map(iterator.next())
                    if (!reject(track) && accept(track)) {
                        process(track, items)
                    }
                }
                row++
            }
        }
    }

    protected fun reject(track: Track): Boolean =
        track.bot || !isDateValid(track) || (track.accountId != null && track.accountId == track.merchantId)

    private fun isDateValid(track: Track): Boolean {
        val trackDate = Instant.ofEpochMilli(track.time).atZone(ZoneId.of("UTC")).toLocalDate()
        return trackDate == date
    }
}
