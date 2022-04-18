package com.wutsi.analytics.tracking.service

import com.opencsv.exceptions.CsvException
import java.io.IOException

interface Importer {
    @Throws(IOException::class, CsvException::class)
    fun import(iterator: InputStreamIterator): Long
}
