package com.wutsi.analytics.tracking.service

import com.wutsi.analytics.tracking.service.iterator.StorageInputStreamIterator
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
    protected abstract fun getOutputFilePath(date: LocalDate): String

    fun aggregate(storage: StorageService): Int {
        val inputUrls = getInputUrls(date, storage)
        if (inputUrls.isEmpty())
            return 0

        val output = ByteArrayOutputStream()
        output.use {
            val count = aggregate(StorageInputStreamIterator(inputUrls, storage), output)
            if (count > 0)
                store(date, ByteArrayInputStream(output.toByteArray()), storage)

            return count
        }
    }

    protected fun getInputUrls(date: LocalDate, urls: MutableList<URL>, storage: StorageService) {
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
            urls.add(url)
        }
    }

    protected fun store(date: LocalDate, input: InputStream, storage: StorageService): URL {
        val path = getOutputFilePath(date)
        return storage.store(path, input, "text/csv")
    }
}
