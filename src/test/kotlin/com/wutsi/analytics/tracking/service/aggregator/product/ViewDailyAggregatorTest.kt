package com.wutsi.analytics.tracking.service.aggregator.product

import com.wutsi.analytics.tracking.service.aggregator.AbstractAggregatorTestBase
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class ViewDailyAggregatorTest : AbstractAggregatorTestBase() {
    override fun getAggregator() = ViewDailyAggregator(LocalDate.of(2020, 4, 14))

    @Test
    fun aggregate() {
        test(
            "/aggregator/daily/view/output.csv",
            "/aggregator/daily/view/2020-04-14-000.csv",
            "/aggregator/daily/view/2020-04-14-001.csv"
        )
    }
}
