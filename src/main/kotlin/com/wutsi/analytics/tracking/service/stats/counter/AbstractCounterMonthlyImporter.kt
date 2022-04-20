package com.wutsi.analytics.tracking.service.stats.counter

import com.wutsi.analytics.tracking.dao.PeriodRepository
import com.wutsi.analytics.tracking.entity.PeriodEntity
import com.wutsi.analytics.tracking.entity.PeriodType
import javax.sql.DataSource

abstract class AbstractCounterMonthlyImporter(
    ds: DataSource,
    dao: PeriodRepository
) : AbstractCounterImporter(ds, dao) {
    override fun getPeriod(year: Int, month: Int): PeriodEntity =
        dao.findByTypeAndYearAndMonth(PeriodType.MONTHLY, year, month)
}
