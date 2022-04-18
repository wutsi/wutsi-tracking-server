package com.wutsi.analytics.tracking.entity

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "T_PRODUCT_STAT")
data class ProductStatEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "period_fk")
    val period: PeriodEntity = PeriodEntity(),

    val productId: Long = 0,
    val merchantId: Long = 0,
    val viewCount: Long = 0,
    val shareCount: Long = 0,
    val chatCount: Long = 0,
)
