package com.wutsi.analytics.tracking.dao

import com.wutsi.analytics.tracking.entity.PeriodEntity
import com.wutsi.analytics.tracking.entity.ProductStatEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductStatRepository : CrudRepository<ProductStatEntity, Long> {
    fun findByPeriodAndProductId(period: PeriodEntity, productId: Long): ProductStatEntity
}
