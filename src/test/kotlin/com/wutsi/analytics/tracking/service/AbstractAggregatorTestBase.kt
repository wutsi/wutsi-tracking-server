package com.wutsi.analytics.tracking.service

import com.amazonaws.util.IOUtils
import com.github.difflib.DiffUtils
import com.github.difflib.patch.Patch
import com.wutsi.analytics.tracking.service.iterator.ClasspathInputStreamIterator
import com.wutsi.platform.core.storage.local.LocalStorageService
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import kotlin.test.BeforeTest
import kotlin.test.assertTrue

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
        assertCsvMatches(expected, ByteArrayInputStream(out.toByteArray()))
    }

    @Throws(Exception::class)
    protected fun assertCsvMatches(expected: InputStream?, value: InputStream?) {
        val patch: Patch<String> = DiffUtils.diff(
            toLines(value),
            toLines(expected)
        )
        println("Delta: " + patch.deltas)
        assertTrue(patch.deltas.isEmpty())
    }

    private fun toLines(`in`: InputStream?): List<String> =
        listOf(IOUtils.toString(`in`).split("\\r?\\n"))
            .filter { !it.isEmpty() }
            .map { it.toString() + "\n" }
}
