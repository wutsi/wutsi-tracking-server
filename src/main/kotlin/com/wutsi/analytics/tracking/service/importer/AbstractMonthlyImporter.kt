package com.wutsi.analytics.tracking.service.importer

import com.opencsv.CSVReader
import com.wutsi.analytics.tracking.service.Importer
import com.wutsi.analytics.tracking.service.InputStreamIterator
import com.wutsi.analytics.tracking.service.aggregator.AbstractCsvMapper
import java.io.InputStreamReader
import java.sql.Connection
import java.sql.PreparedStatement
import javax.sql.DataSource

abstract class AbstractMonthlyImporter<T>(
    private val ds: DataSource
) : Importer {
    protected abstract fun prepareStatement(cnn: Connection): PreparedStatement
    protected abstract fun createCsvMapper(): AbstractCsvMapper<T>
    protected abstract fun map(item: T, stmt: PreparedStatement)
    protected abstract fun log(item: T, ex: Throwable? = null)

    override fun import(iterator: InputStreamIterator): Long {
        val cnn = ds.connection
        try {

            val stmt = prepareStatement(cnn)
            try {
                return import(iterator, stmt)
            } finally {
                stmt.close()
            }
        } finally {
            cnn.close()
        }
    }

    private fun import(iterator: InputStreamIterator, stmt: PreparedStatement): Long {
        var imported = 0L
        while (iterator.hasNext()) {
            val reader = InputStreamReader(iterator.next())
            reader.use {
                val csv = CSVReader(reader)
                csv.use {
                    imported += import(csv, stmt)
                }
            }
        }
        return imported
    }

    private fun import(csv: CSVReader, stmt: PreparedStatement): Long {
        val iterator: Iterator<Array<String>> = csv.iterator()
        var row = 0
        val mapper = createCsvMapper()
        var imported = 0L
        while (iterator.hasNext()) {
            val data = iterator.next()
            if (row == 0) {
                mapper.column(data)
            } else {
                // Load the data
                val item = mapper.map(data)
                try {
                    map(item, stmt)
                    stmt.executeUpdate()
                    log(item)
                    imported++
                } catch (ex: Exception) {
                    log(item, ex)
                }
            }
            row++
        }
        return imported
    }

    private fun key(year: Int, month: Int) = "$year$month"
}
