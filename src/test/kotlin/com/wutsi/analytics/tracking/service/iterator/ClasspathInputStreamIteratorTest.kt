package com.wutsi.analytics.tracking.service.iterator

import com.wutsi.analytics.tracking.service.InputStreamIterator
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertNotNull

internal class ClasspathInputStreamIteratorTest {
    @Test
    @Throws(Exception::class)
    operator fun next() {
        val it: InputStreamIterator = ClasspathInputStreamIterator(
            listOf(
                "/aggregator/daily/view/2020-04-14-000.csv",
                "/aggregator/daily/view/2020-04-14-001.csv",
            )
        )
        assertNotNull(it.next())
        assertNotNull(it.next())
        assertThrows<NoSuchElementException> { it.next() }
    }

    @Test
    @Throws(Exception::class)
    fun nextWithInvalidPath() {
        val it: InputStreamIterator = ClasspathInputStreamIterator(
            listOf(
                "/aggregator/daily/view/2020-04-14-000.csv",
                "/tracks/xxxxx",
                "/aggregator/daily/view/2020-04-14-001.csv",
                "/tracks/yyyy"
            )
        )
        assertNotNull(it.next())
        assertNotNull(it.next())
        assertThrows<NoSuchElementException> { it.next() }
    }
}
