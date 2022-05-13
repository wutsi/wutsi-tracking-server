package com.wutsi.analytics.tracking.service

import com.opencsv.CSVWriter
import com.wutsi.analytics.tracking.dto.Track
import org.springframework.stereotype.Service
import java.io.OutputStream
import java.io.OutputStreamWriter

@Service
class TrackWriter {
    fun write(items: List<Track>, out: OutputStream) {
        val writer = OutputStreamWriter(out)
        val csv = CSVWriter(
            writer,
            CSVWriter.DEFAULT_SEPARATOR,
            CSVWriter.DEFAULT_QUOTE_CHARACTER,
            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
            CSVWriter.DEFAULT_LINE_END
        )
        csv.use {
            headers(csv)
            data(items, csv)
        }
    }

    private fun headers(csv: CSVWriter) {
        csv.writeNext(
            arrayOf(
                "time",
                "tenantid",
                "correlationid",
                "deviceid",
                "accountid",
                "merchantid",
                "productid",
                "page",
                "event",
                "value",
                "ip",
                "long",
                "lat",
                "referer",
                "bot",
                "ua",
                "url",
                "impressions",
                "devicetype",
                "source"
            )
        )
    }

    private fun data(items: List<Track>, csv: CSVWriter) {
        items.forEach { data(it, csv) }
    }

    private fun data(track: Track, csv: CSVWriter) {
        csv.writeNext(
            arrayOf(
                string(track.time),
                string(track.tenantId),
                string(track.correlationId),
                string(track.deviceId),
                string(track.accountId),
                string(track.merchantId),
                string(track.productId),
                string(track.page),
                string(track.event),
                string(track.value),
                string(track.ip),
                string(track.long),
                string(track.lat),
                string(track.referer),
                string(track.bot),
                string(track.ua),
                string(track.url),
                string(track.impressions),
                string(track.deviceType),
                string(track.source)
            )
        )
    }

    private fun string(s: Long?) = s?.toString() ?: ""

    private fun string(s: Double?) = s?.toString() ?: ""

    private fun string(s: String?) = s ?: ""

    private fun string(s: Boolean) = s.toString()
}
