package com.wutsi.analytics.tracking.service

import com.wutsi.analytics.tracking.service.iterator.StorageInputStreamIterator
import com.wutsi.platform.core.logging.DefaultKVLogger
import com.wutsi.platform.core.logging.KVLogger
import com.wutsi.platform.core.storage.StorageService
import com.wutsi.platform.core.storage.StorageVisitor
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter

abstract class AbstractAggregator<T>(private val date: LocalDate) : Aggregator {
    protected abstract fun getInputUrls(date: LocalDate, storage: StorageService): List<URL>
    protected abstract fun acceptInputURL(url: URL): Boolean
    protected abstract fun getOutputFilePath(date: LocalDate): String

    protected open fun beforeAggregate(storage: StorageService, logger: KVLogger) {
    }

    fun aggregate(storage: StorageService): Int {
        val logger = DefaultKVLogger()
        val inputUrls = getInputUrls(date, storage)
        val outputPath = getOutputFilePath(date)

        logger.add("aggregator", javaClass.simpleName)
        logger.add("date", date)
        logger.add("output_path", outputPath)
        logger.add("input_urls", inputUrls)
        beforeAggregate(storage, logger)
        try {
            var count = 0
            if (inputUrls.isNotEmpty()) {
                val output = ByteArrayOutputStream()
                output.use {
                    count = aggregate(StorageInputStreamIterator(inputUrls, storage), output)
                    if (count > 0)
                        store(outputPath, ByteArrayInputStream(output.toByteArray()), storage)
                }
            }

            logger.add("count", count)
            logger.add("success", true)
            return count

        } catch (ex: Exception) {
            logger.add("success", false)
            logger.setException(ex)
            return 0
        } finally {
            logger.log()
        }
    }

    fun getInputUrls(date: LocalDate, urls: MutableList<URL>, storage: StorageService) {
        val path = "tracks/" + date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        val found = collectFilesURLs(path, storage)
        urls.addAll(found)
    }

    protected fun collectFilesURLs(path: String, storage: StorageService): List<URL> {
        val urls: MutableList<URL> = mutableListOf()
        val visitor = createVisitor(urls)
        storage.visit(path, visitor)

        return urls
    }

    protected fun createVisitor(urls: MutableList<URL>) = object : StorageVisitor {
        override fun visit(url: URL) {
            if (acceptInputURL(url))
                urls.add(url)
        }
    }

    protected fun store(path: String, input: InputStream, storage: StorageService): URL =
        storage.store(path, input, "text/csv")
}
