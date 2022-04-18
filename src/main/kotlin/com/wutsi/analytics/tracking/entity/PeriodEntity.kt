package com.wutsi.analytics.tracking.entity

import javax.persistence.Entity
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "T_PERIOD")
data class PeriodEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Enumerated
    val type: PeriodType = PeriodType.YEARLY,

    val year: Int = 0,
    val month: Int = 0,
)
