package com.wutsi.analytics.tracking.service.agregator.visitor

import com.wutsi.analytics.tracking.service.aggregator.AbstractAggregatorTestBase
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class VisitorAggregatorTest : AbstractAggregatorTestBase() {
    override fun getAggregator() = VisitorAggregator(LocalDate.of(2020, 4, 14))

    @Test
    fun aggregate() {
        test(
            "/aggregator/visitor/output.csv",
            "/aggregator/visitor/2020-04-14-000.csv",
            "/aggregator/visitor/2020-04-14-001.csv"
        )
    }
}
