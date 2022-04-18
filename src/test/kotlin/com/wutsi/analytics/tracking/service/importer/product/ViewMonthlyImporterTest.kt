package com.wutsi.analytics.tracking.service.importer.product

import com.wutsi.analytics.tracking.dao.PeriodRepository
import com.wutsi.analytics.tracking.dao.ProductStatRepository
import com.wutsi.analytics.tracking.entity.PeriodType
import com.wutsi.analytics.tracking.service.iterator.ClasspathInputStreamIterator
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/sql/clean.sql", "/sql/ViewMonthlyImporter.sql"])
internal class ViewMonthlyImporterTest {
    @Autowired
    private lateinit var importer: ViewMonthlyImporter

    @Autowired
    private lateinit var periodDao: PeriodRepository

    @Autowired
    private lateinit var statDao: ProductStatRepository

    @Test
    fun import() {
        val iterator = ClasspathInputStreamIterator(
            listOf(
                "/importer/view.csv",
            )
        )
        importer.import(iterator)

        assertMonthlyStat(2020, 4, 1, 9, 555)
        assertMonthlyStat(2020, 4, 2, 2, 666)
        assertMonthlyStat(2020, 4, 3, 11, 777)
    }

    private fun assertMonthlyStat(year: Int, month: Int, productId: Long, value: Long, merchantId: Long) {
        val period = periodDao.findByTypeAndYearAndMonth(PeriodType.MONTHLY, year, month)
        val stat = statDao.findByPeriodAndProductId(period, productId)

        assertEquals(value, stat.viewCount)
        assertEquals(merchantId, stat.merchantId)
    }
}
