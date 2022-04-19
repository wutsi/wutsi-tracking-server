package com.wutsi.analytics.tracking.dao

import com.wutsi.analytics.tracking.entity.PeriodEntity
import com.wutsi.analytics.tracking.entity.PeriodType
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PeriodRepository : CrudRepository<PeriodEntity, Long> {
    fun findByTypeAndYear(type: PeriodType, year: Int): PeriodEntity
    fun findByTypeAndYearAndMonth(type: PeriodType, year: Int, month: Int): PeriodEntity
}
