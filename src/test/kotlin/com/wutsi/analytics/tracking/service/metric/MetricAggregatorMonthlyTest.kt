package com.wutsi.analytics.tracking.service.metric

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.analytics.tracking.service.AbstractAggregatorTestBase
import org.junit.jupiter.api.Test
import java.io.File
import java.time.LocalDate
import kotlin.test.assertTrue

internal class MetricAggregatorMonthlyTest : AbstractAggregatorTestBase() {
    override fun getAggregator() = MetricAggregatorMonthly(MetricType.VIEW, LocalDate.of(2020, 4, 14))

    @Test
    fun aggregate() {
        test(
            "/aggregator/monthly/view/output.csv",
            "/aggregator/monthly/view/2020-04-14.csv",
            "/aggregator/monthly/view/2020-04-15.csv",
            "/aggregator/monthly/view/2020-04-16.csv",
        )
    }

    @Test
    fun merge() {
        storage.store(
            "/aggregates/daily/2020/04/14/view-000.csv",
            javaClass.getResourceAsStream("/aggregator/daily/view/output.csv")
        )

        getAggregator().aggregate(storage)

        assertTrue(File("$storageDirectory/aggregates/monthly/2020/04/view.csv").exists())
    }
}
