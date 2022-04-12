package com.wutsi.analytics.tracking.service.agregator.visit

import com.wutsi.analytics.tracking.service.aggregator.AbstractAggregatorTestBase
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class VisitDailyAggregatorTest : AbstractAggregatorTestBase() {
    override fun getAggregator() = VisitDailyAggregator(LocalDate.of(2020, 4, 14))

    @Test
    fun aggregate() {
        test(
            "/aggregator/daily/visit/output.csv",
            "/aggregator/daily/visit/2020-04-14-000.csv",
            "/aggregator/daily/visit/2020-04-14-001.csv"
        )
    }
}
