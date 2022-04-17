package com.wutsi.analytics.tracking.service.aggregator.product

import com.opencsv.exceptions.CsvException
import com.wutsi.analytics.tracking.dto.Track
import com.wutsi.analytics.tracking.entity.EventType
import com.wutsi.analytics.tracking.service.InputStreamIterator
import com.wutsi.analytics.tracking.service.aggregator.AbstractDailyAggregator
import java.io.IOException
import java.io.OutputStream
import java.time.LocalDate

abstract class AbstractCounterDailyAggregator(date: LocalDate) : AbstractDailyAggregator<Counter>(date) {
    protected abstract fun getEventType(): EventType

    override fun accept(track: Track): Boolean =
        getEventType().name.equals(track.event, true) &&
            track.productId != null

    override fun process(track: Track, items: MutableMap<String, Counter>) {
        val key = getKey(track)
        if (!items.containsKey(key))
            items[key] = Counter(
                time = track.time,
                tenantId = track.tenantId,
                accountId = track.accountId,
                merchantId = track.merchantId,
                productId = track.productId
            )
        else
            items[key]!!.count++
    }

    @Throws(IOException::class, CsvException::class)
    override fun aggregate(iterator: InputStreamIterator, output: OutputStream) {
        val items = loadItems(iterator)
        if (items.isNotEmpty())
            CounterWriter().write(items, output)
    }

    protected open fun getKey(track: Track): String =
        track.productId.toString()
}
