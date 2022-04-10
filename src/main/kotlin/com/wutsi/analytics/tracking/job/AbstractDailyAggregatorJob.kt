package com.wutsi.analytics.tracking.job

import com.wutsi.analytics.tracking.service.Aggregator
import com.wutsi.analytics.tracking.service.iterator.StorageInputStreamIterator
import com.wutsi.platform.core.logging.DefaultKVLogger
import com.wutsi.platform.core.storage.StorageService
import com.wutsi.platform.core.storage.StorageVisitor
import org.springframework.beans.factory.annotation.Autowired
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.URL
import java.time.Clock
import java.time.LocalDate
import java.time.format.DateTimeFormatter

abstract class AbstractDailyAggregatorJob {
    @Autowired
    private lateinit var storage: StorageService

    @Autowired
    private lateinit var clock: Clock

    protected abstract fun getJobName(): String
    protected abstract fun getAggregator(date: LocalDate): Aggregator

    open fun run() {
        val logger = DefaultKVLogger()
        val date = LocalDate.now(clock).minusDays(1)
        val aggregator = getAggregator(date)
        val urls = inputUrls(date)

        logger.add("job", getJobName())
        logger.add("date", date)
        logger.add("aggregator", aggregator.javaClass.name)
        logger.add("url_count", urls.size)

        try {
            if (urls.isEmpty())
                return

            val output = ByteArrayOutputStream()
            output.use {
                aggregator.aggregate(StorageInputStreamIterator(urls, storage), output)
                val outputUrl = store(date, ByteArrayInputStream(output.toByteArray()))
                logger.add("output_url", outputUrl)
            }
        } catch (ex: Exception) {
            logger.setException(ex)
        } finally {
            logger.log()
        }
    }

    private fun store(date: LocalDate, input: InputStream): URL {
        val path = outputFilePath(date)
        return storage.store(path, input, "text/csv")
    }

    private fun outputFilePath(date: LocalDate): String {
        val filepath = date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        val filename = getJobName() + ".csv"
        return "aggregates/$filepath/$filename"
    }

    private fun inputUrls(date: LocalDate): List<URL> {
        val urls = mutableListOf<URL>()

        getInputUrls(date, urls)
        getInputUrls(date.plusDays(1), urls)
        return urls
    }

    private fun getInputUrls(date: LocalDate, urls: MutableList<URL>) {
        val path = "tracks/" + date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        val found = collectFilesURLs(path)
        urls.addAll(found)
    }

    private fun collectFilesURLs(path: String): List<URL> {
        val urls: MutableList<URL> = mutableListOf()
        val visitor = createVisitor(urls)
        storage.visit(path, visitor)

        return urls
    }

    private fun createVisitor(urls: MutableList<URL>) = object : StorageVisitor {
        override fun visit(url: URL) {
            urls.add(url)
        }
    }
}
