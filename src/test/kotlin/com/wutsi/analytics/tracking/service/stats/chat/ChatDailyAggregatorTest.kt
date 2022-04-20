package com.wutsi.analytics.tracking.service.stats.chat

import com.wutsi.analytics.tracking.service.AbstractAggregatorTestBase
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class ChatDailyAggregatorTest : AbstractAggregatorTestBase() {
    override fun getAggregator() = ChatDailyAggregator(LocalDate.of(2020, 4, 14))

    @Test
    fun aggregate() {
        test(
            "/aggregator/daily/chat/output.csv",
            "/aggregator/daily/chat/2020-04-14-000.csv",
            "/aggregator/daily/chat/2020-04-14-001.csv"
        )
    }
}
