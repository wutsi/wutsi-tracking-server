package com.wutsi.analytics.tracking.service.aggregator

import com.amazonaws.util.IOUtils
import com.github.difflib.DiffUtils
import com.github.difflib.patch.Patch
import com.wutsi.analytics.tracking.service.Aggregator
import com.wutsi.analytics.tracking.service.iterator.ClasspathInputStreamIterator
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import kotlin.test.assertTrue

abstract class AbstractAggregatorTestBase {
    protected abstract fun getAggregator(): Aggregator

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
