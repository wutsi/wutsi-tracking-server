package com.wutsi.analytics.tracking.service.importer.product

import com.wutsi.analytics.tracking.dao.PeriodRepository
import com.wutsi.analytics.tracking.entity.EventType
import com.wutsi.analytics.tracking.entity.PeriodEntity
import com.wutsi.analytics.tracking.entity.PeriodType
import com.wutsi.analytics.tracking.service.aggregator.AbstractCsvMapper
import com.wutsi.analytics.tracking.service.aggregator.product.Counter
import com.wutsi.analytics.tracking.service.aggregator.product.CounterCsvMapper
import com.wutsi.analytics.tracking.service.importer.AbstractMonthlyImporter
import java.sql.Connection
import java.sql.PreparedStatement
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.sql.DataSource

abstract class AbstractCounterMonthlyImporter(
    ds: DataSource,
    private val dao: PeriodRepository
) : AbstractMonthlyImporter<Counter>(ds) {
    abstract fun getEventType(): EventType

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

    private fun getPeriod(year: Int, month: Int): PeriodEntity =
        dao.findByTypeAndYearAndMonth(PeriodType.MONTHLY, year, month)
}
