package com.wutsi.analytics.tracking.service.stats.counter

import com.wutsi.analytics.tracking.dao.PeriodRepository
import com.wutsi.analytics.tracking.entity.EventType
import com.wutsi.analytics.tracking.entity.PeriodEntity
import com.wutsi.analytics.tracking.service.stats.AbstractCsvMapper
import com.wutsi.analytics.tracking.service.stats.AbstractMonthlyImporter
import java.sql.Connection
import java.sql.PreparedStatement
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.sql.DataSource

abstract class AbstractCounterImporter(
    ds: DataSource,
    protected val dao: PeriodRepository
) : AbstractMonthlyImporter<Counter>(ds) {
    abstract fun getEventType(): EventType
    abstract fun getPeriod(year: Int, month: Int): PeriodEntity

    override fun prepareStatement(cnn: Connection): PreparedStatement {
        val column = getEventType().name.lowercase() + "_count"
        val sql = "INSERT INTO T_PRODUCT_STAT(period_fk, product_id, merchant_id, $column)\n" +
            "VALUES(?, ?, ?, ?)\n" +
            "ON CONFLICT (period_fk, product_id) DO UPDATE SET\n" +
            "$column=?,\n" +
            "merchant_id=?"
        return cnn.prepareStatement(sql)
    }

    override fun createCsvMapper(): AbstractCsvMapper<Counter> = CounterCsvMapper()

    override fun map(item: Counter, stmt: PreparedStatement) {
        val date = toLocalDate(item)
        stmt.setLong(1, getPeriod(date.year, date.monthValue).id!!)
        stmt.setLong(2, item.productId?.toLong() ?: -1)
        stmt.setLong(3, item.merchantId?.toLong() ?: -1)
        stmt.setLong(4, item.count)
        stmt.setLong(5, item.count)
        stmt.setLong(6, item.merchantId?.toLong() ?: -1)
    }

    private fun toLocalDate(item: Counter): LocalDate =
        Instant.ofEpochMilli(item.time).atZone(ZoneId.of("UTC")).toLocalDate()
}