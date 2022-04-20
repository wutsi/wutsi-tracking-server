package com.wutsi.analytics.tracking.service.stats.metric

import com.wutsi.analytics.tracking.service.AbstractAggregatorTestBase
import org.junit.jupiter.api.Test

internal class MetricMonthlyAggregatorTest : AbstractAggregatorTestBase() {
    override fun getAggregator() = MetricMonthlyAggregator()

    @Test
    fun aggregate() {
        test(
            "/aggregator/monthly/view/output.csv",
            "/aggregator/monthly/view/2020-04-14.csv",
            "/aggregator/monthly/view/2020-04-15.csv",
            "/aggregator/monthly/view/2020-04-16.csv",
        )
    }
}
