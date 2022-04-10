package com.wutsi.analytics.tracking.service

import java.io.IOException
import java.io.InputStream

interface InputStreamIterator {
    @Throws(IOException::class, NoSuchElementException::class)
    operator fun next(): InputStream
    operator fun hasNext(): Boolean
}
