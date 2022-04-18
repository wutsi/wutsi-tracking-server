package com.wutsi.analytics.tracking.service.importer.product

import com.wutsi.analytics.tracking.dao.PeriodRepository
import com.wutsi.analytics.tracking.entity.EventType
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class ChatMonthlyImporter(
    ds: DataSource,
    dao: PeriodRepository
) : AbstractCounterMonthlyImporter(ds, dao) {
    override fun getEventType() = EventType.CHAT
}
