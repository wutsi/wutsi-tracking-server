package com.wutsi.analytics.tracking.service.iterator

import com.wutsi.analytics.tracking.service.InputStreamIterator
import java.io.InputStream

class ClasspathInputStreamIterator(
    paths: List<String>
) : InputStreamIterator {
    private val iterator: Iterator<InputStream> = paths
        .map { ClasspathInputStreamIterator::class.java.getResourceAsStream(it) }
        .filterNotNull()
        .iterator()

    override fun next(): InputStream =
        iterator.next()

    override fun hasNext(): Boolean =
        iterator.hasNext()
}
