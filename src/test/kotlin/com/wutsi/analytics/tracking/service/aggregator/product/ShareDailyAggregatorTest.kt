package com.wutsi.analytics.tracking.service.aggregator.product

import com.wutsi.analytics.tracking.service.aggregator.AbstractAggregatorTestBase
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class ShareDailyAggregatorTest : AbstractAggregatorTestBase() {
    override fun getAggregator() = ShareDailyAggregator(LocalDate.of(2020, 4, 14))

    @Test
    fun aggregate() {
        test(
            "/aggregator/daily/share/output.csv",
            "/aggregator/daily/share/2020-04-14-000.csv",
            "/aggregator/daily/share/2020-04-14-001.csv"
        )
    }
}
