package com.wutsi.analytics.tracking.service

import com.amazonaws.util.IOUtils
import com.wutsi.analytics.tracking.service.iterator.ClasspathInputStreamIterator
import com.wutsi.platform.core.storage.local.LocalStorageService
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

abstract class AbstractAggregatorTestBase {
    protected val storageDirectory: String = System.getProperty("user.home") + "/wutsi/storage"
    protected val storage = LocalStorageService(storageDirectory, "http://localhost:0")

    protected abstract fun getAggregator(): Aggregator

    @BeforeTest
    fun setUp() {
        File(storageDirectory).deleteRecursively()
    }

    @Throws(Exception::class)
    protected fun test(expectedPath: String, vararg paths: String) {
        val iterator = ClasspathInputStreamIterator(paths.toList())
        val out = ByteArrayOutputStream()

        // WHEN
        getAggregator().aggregate(iterator, out)

        // THEN
        val expected = javaClass.getResourceAsStream(expectedPath)
        assertFileMatches(expected, ByteArrayInputStream(out.toByteArray()))
    }

    @Throws(Exception::class)
    protected fun assertFileMatches(expected: InputStream?, value: InputStream?) {
        assertEquals(toString(expected), toString(value))
    }

    private fun toString(`in`: InputStream?): String =
        IOUtils.toString(`in`).trimIndent()
}
