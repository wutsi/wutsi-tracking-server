package com.wutsi.analytics.tracking.service.stats.view

import com.wutsi.analytics.tracking.dao.PeriodRepository
import com.wutsi.analytics.tracking.entity.EventType
import com.wutsi.analytics.tracking.service.stats.counter.AbstractCounterMonthlyImporter
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class ViewMonthlyImporter(
    ds: DataSource,
    dao: PeriodRepository
) : AbstractCounterMonthlyImporter(ds, dao) {
    override fun getEventType() = EventType.VIEW
}
