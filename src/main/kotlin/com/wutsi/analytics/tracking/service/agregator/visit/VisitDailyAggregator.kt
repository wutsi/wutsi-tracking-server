package com.wutsi.analytics.tracking.service.agregator.visit

import com.opencsv.exceptions.CsvException
import com.wutsi.analytics.tracking.dto.Track
import com.wutsi.analytics.tracking.entity.EventType
import com.wutsi.analytics.tracking.service.InputStreamIterator
import com.wutsi.analytics.tracking.service.agregator.AbstractDailyAggregator
import java.io.IOException
import java.io.OutputStream
import java.time.LocalDate

/**
 * Aggregate the number visits by product
 */
open class VisitDailyAggregator(date: LocalDate) : AbstractDailyAggregator<Visit>(date) {
    override fun accept(track: Track): Boolean =
        EventType.VIEW.name.equals(track.event, true) &&
            track.productId != null

    override fun process(track: Track, items: MutableMap<String, Visit>) {
        val key = getKey(track)
        if (!items.containsKey(key))
            items[key] = Visit(
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
        VisitWriter().write(items, output)
    }

    protected open fun getKey(track: Track): String =
        track.productId.toString()
}
