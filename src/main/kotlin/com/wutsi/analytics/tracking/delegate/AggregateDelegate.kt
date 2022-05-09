package com.wutsi.analytics.tracking.`delegate`

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.analytics.tracking.service.metric.MetricAggregatorDaily
import com.wutsi.analytics.tracking.service.metric.MetricAggregatorMonthly
import com.wutsi.analytics.tracking.service.metric.MetricAggregatorOverall
import com.wutsi.analytics.tracking.service.metric.MetricAggregatorYearly
import com.wutsi.platform.core.storage.StorageService
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.LocalDate

@Service
class AggregateDelegate(
    private val storage: StorageService,
    private val clock: Clock
) {
    @Async
    fun invoke(startDate: LocalDate) {
        val endDate = LocalDate.now(clock)
        daily(startDate, endDate)
        monthly(startDate, endDate)
        yearly(startDate, endDate)
        overall(startDate)
    }

    private fun daily(startDate: LocalDate, endDate: LocalDate) {
        var cur = startDate
        while (cur.isBefore(endDate)) {
            MetricType.values().forEach {
                MetricAggregatorDaily(it, cur).aggregate(storage)
            }
            cur = cur.plusDays(1)
        }
    }

    private fun monthly(startDate: LocalDate, endDate: LocalDate) {
        var cur = startDate
        while (cur.isBefore(endDate)) {
            MetricType.values().forEach {
                MetricAggregatorMonthly(it, cur).aggregate(storage)
            }
            cur = cur.plusMonths(1)
        }
    }

    private fun yearly(startDate: LocalDate, endDate: LocalDate) {
        var cur = startDate
        while (cur.isBefore(endDate)) {
            MetricType.values().forEach {
                MetricAggregatorYearly(it, cur).aggregate(storage)
            }
            cur = cur.plusYears(1)
        }
    }

    private fun overall(startDate: LocalDate) {
        MetricType.values().forEach {
            MetricAggregatorOverall(it, startDate).aggregate(storage)
        }
    }
}
