package com.wutsi.analytics.tracking.service.importer.product

import com.wutsi.analytics.tracking.dao.PeriodRepository
import com.wutsi.analytics.tracking.entity.PeriodEntity
import com.wutsi.analytics.tracking.entity.PeriodType
import javax.sql.DataSource

abstract class AbstractCounterOverallImporter(
    ds: DataSource,
    dao: PeriodRepository
) : AbstractCounterImporter(ds, dao) {
    override fun getPeriod(year: Int, month: Int): PeriodEntity =
        dao.findByTypeAndYear(PeriodType.OVERALL, 0)
}
