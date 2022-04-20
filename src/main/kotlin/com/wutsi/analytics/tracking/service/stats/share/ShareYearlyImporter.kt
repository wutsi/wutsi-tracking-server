package com.wutsi.analytics.tracking.service.stats.share

import com.wutsi.analytics.tracking.dao.PeriodRepository
import com.wutsi.analytics.tracking.entity.EventType
import com.wutsi.analytics.tracking.service.stats.counter.AbstractCounterYearlyImporter
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class ShareYearlyImporter(
    ds: DataSource,
    dao: PeriodRepository
) : AbstractCounterYearlyImporter(ds, dao) {
    override fun getEventType() = EventType.SHARE
}
