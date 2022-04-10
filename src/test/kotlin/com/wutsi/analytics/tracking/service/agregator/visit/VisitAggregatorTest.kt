package com.wutsi.analytics.tracking.service.agregator.visit

import com.wutsi.analytics.tracking.service.aggregator.AbstractAggregatorTestBase
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class VisitAggregatorTest : AbstractAggregatorTestBase() {
    override fun getAggregator() = VisitAggregator(LocalDate.of(2020, 4, 14))

    @Test
    fun aggregate() {
        test(
            "/aggregator/visit/output.csv",
            "/aggregator/visit/2020-04-14-000.csv",
            "/aggregator/visit/2020-04-14-001.csv"
        )
    }
}
