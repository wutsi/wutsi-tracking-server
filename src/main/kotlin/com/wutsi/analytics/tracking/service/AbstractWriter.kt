package com.wutsi.analytics.tracking.service

import com.opencsv.CSVWriter
import com.opencsv.exceptions.CsvException
import java.io.IOException
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.Writer

abstract class AbstractWriter<T> {
    @Throws(IOException::class)
    protected abstract fun headers(): Array<String>

    protected abstract fun row(data: T): Array<String?>

    @Throws(IOException::class, CsvException::class)
    open fun write(data: List<T>, out: OutputStream) {
        val writer: Writer = OutputStreamWriter(out)
        writer.use {
            val csv = CSVWriter(writer)
            csv.writeNext(headers())
            data.forEach {
                csv.writeNext(row(it))
            }
        }
    }
}
