package com.wutsi.analytics.tracking.service.iterator

import com.nhaarman.mockitokotlin2.mock
import com.wutsi.platform.core.storage.StorageService
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.net.URL

internal class StorageInputStreamIteratorTest {
    private lateinit var storage: StorageService
    private lateinit var iterator: StorageInputStreamIterator

    @BeforeEach
    fun setUp() {
        storage = mock()

        val urls = mutableListOf<URL>()
        urls.add(URL("http://www.google.ca/a.txt"))
        iterator = StorageInputStreamIterator(urls, storage)
    }

    @Test
    operator fun next() {
        assertNotNull(iterator.next())
        assertThrows<NoSuchElementException> { iterator.next() }
    }

    @Test
    @Throws(Exception::class)
    fun hasNext() {
        assertTrue(iterator.hasNext())
    }

    @Test
    @Throws(Exception::class)
    fun hasNextFalse() {
        iterator.next()
        assertFalse(iterator.hasNext())
    }
}
