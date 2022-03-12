package com.wutsi.analytics.tracking.service

import com.wutsi.analytics.tracking.dto.Track
import com.wutsi.platform.core.storage.StorageService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone
import java.util.UUID

@Service
class TrackPersister(
    private val writer: TrackWriter,
    private val storage: StorageService,
) {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(TrackPersister::class.java)
    }

    fun persist(items: List<Track>) {
        if (items.isEmpty())
            return

        val out = ByteArrayOutputStream()
        writer.write(items, out)
        return persist(items, ByteArrayInputStream(out.toByteArray()))
    }

    private fun persist(items: List<Track>, input: InputStream) {
        val fmt = SimpleDateFormat("yyyy/MM/dd")
        fmt.timeZone = TimeZone.getTimeZone("UTC")

        val folder = fmt.format(Date())
        val file = UUID.randomUUID().toString() + ".csv"
        val path = "$folder/$file"
        val url = storage.store(path, input, "text/csv", Int.MAX_VALUE)

        LOGGER.info("Storing ${items.size} track(s) to $url")
    }
}
