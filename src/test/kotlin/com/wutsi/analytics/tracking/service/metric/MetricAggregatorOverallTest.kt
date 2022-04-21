package com.wutsi.analytics.tracking.service.metric

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.analytics.tracking.service.AbstractAggregatorTestBase
import org.junit.jupiter.api.Test
import java.io.File
import java.time.LocalDate
import kotlin.test.assertTrue

internal class MetricAggregatorOverallTest : AbstractAggregatorTestBase() {
    override fun getAggregator() = MetricAggregatorOverall(MetricType.VIEW, LocalDate.of(2020, 4, 14))

    @Test
    fun merge() {
        storage.store(
            "/aggregates/yearly/2020/view-000.csv",
            javaClass.getResourceAsStream("/aggregator/daily/view/output.csv")
        )

        getAggregator().aggregate(storage)

        assertTrue(File("$storageDirectory/aggregates/overall/view.csv").exists())
    }
}
