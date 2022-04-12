package com.wutsi.analytics.tracking.service.agregator.visit

import com.wutsi.analytics.tracking.service.aggregator.AbstractAggregatorTestBase
import org.junit.jupiter.api.Test

internal class VisitMonthlyAggregatorTest : AbstractAggregatorTestBase() {
    override fun getAggregator() = VisitMonthlyAggregator()

    @Test
    fun aggregate() {
        test(
            "/aggregator/monthly/visit/output.csv",
            "/aggregator/monthly/visit/2020-04-14.csv",
            "/aggregator/monthly/visit/2020-04-15.csv",
            "/aggregator/monthly/visit/2020-04-16.csv",
        )
    }
}
