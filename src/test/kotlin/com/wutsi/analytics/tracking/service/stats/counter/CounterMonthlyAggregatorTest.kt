package com.wutsi.analytics.tracking.service.stats.counter

import com.wutsi.analytics.tracking.service.AbstractAggregatorTestBase
import org.junit.jupiter.api.Test

internal class CounterMonthlyAggregatorTest : AbstractAggregatorTestBase() {
    override fun getAggregator() = CounterMonthlyAggregator()

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
