package com.wutsi.analytics.tracking.job.step

import com.wutsi.analytics.tracking.service.Aggregator
import com.wutsi.analytics.tracking.service.Importer
import com.wutsi.analytics.tracking.service.iterator.StorageInputStreamIterator
import com.wutsi.platform.core.logging.DefaultKVLogger
import com.wutsi.platform.core.logging.KVLogger
import com.wutsi.platform.core.storage.StorageService
import com.wutsi.platform.core.storage.StorageVisitor
import org.springframework.beans.factory.annotation.Autowired
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.URL
import java.time.Clock
import java.time.LocalDate

abstract class AbstractAggregatorJob {
    @Autowired
    protected lateinit var storage: StorageService

    @Autowired
    protected lateinit var clock: Clock

    protected abstract fun getName(): String

    protected abstract fun getInputUrls(date: LocalDate): List<URL>

    protected abstract fun getOutputFilePath(date: LocalDate): String

    protected abstract fun getAggregator(date: LocalDate): Aggregator

    protected abstract fun getImporter(): Importer?

    open fun run() {
        val logger = DefaultKVLogger()
        val date = LocalDate.now(clock).minusDays(1)
        val urls = getInputUrls(date)

        logger.add("job", javaClass.simpleName)
        logger.add("date", date)
        logger.add("file_count", urls.size)
        try {
            val outputUrl = aggregate(date, urls, logger)
            if (outputUrl != null) {
                val imported = import(outputUrl, logger)
            }
        } catch (ex: Exception) {
            logger.setException(ex)
        } finally {
            logger.log()
        }
    }

    private fun aggregate(date: LocalDate, urls: List<URL>, logger: KVLogger): URL? {
        val aggregator = getAggregator(date)
        val output = ByteArrayOutputStream()
        output.use {
            val count = aggregator.aggregate(StorageInputStreamIterator(urls, storage), output)
            val url = if (count > 0)
                store(date, ByteArrayInputStream(output.toByteArray()))
            else
                null

            logger.add("aggregate_count", count)
            logger.add("aggregate_url", url)
            return url
        }
    }

    private fun import(url: URL, logger: KVLogger) {
        val count = getImporter()?.import(StorageInputStreamIterator(listOf(url), storage))
        logger.add("import_count", count)
    }

    protected fun store(date: LocalDate, input: InputStream): URL {
        val path = getOutputFilePath(date)
        return storage.store(path, input, "text/csv")
    }

    protected fun collectFilesURLs(path: String): List<URL> {
        val urls: MutableList<URL> = mutableListOf()
        val visitor = createVisitor(urls)
        storage.visit(path, visitor)

        return urls
    }

    protected fun createVisitor(urls: MutableList<URL>) = object : StorageVisitor {
        override fun visit(url: URL) {
            urls.add(url)
        }
    }
}
