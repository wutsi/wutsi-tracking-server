package com.wutsi.analytics.tracking.service

import com.opencsv.exceptions.CsvException
import java.io.IOException
import java.io.OutputStream

interface Aggregator {
    @Throws(IOException::class, CsvException::class)
    fun aggregate(iterator: InputStreamIterator, output: OutputStream): Int
}
