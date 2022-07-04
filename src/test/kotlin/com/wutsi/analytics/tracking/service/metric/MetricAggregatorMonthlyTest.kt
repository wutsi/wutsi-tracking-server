package com.wutsi.analytics.tracking.service.metric

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.analytics.tracking.service.AbstractAggregatorTestBase
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.File
import java.time.LocalDate
import kotlin.test.assertTrue

internal class MetricAggregatorMonthlyTest : AbstractAggregatorTestBase() {
    override fun getAggregator() = MetricAggregatorMonthly(MetricType.VIEW, LocalDate.of(2020, 4, 14))

    @Test
    fun aggregate() {
        // GIVEN
        storage.store(
            "/aggregates/daily/2020/04/14/view.csv",
            javaClass.getResourceAsStream("/aggregator/monthly/view/2020-04-14.csv")
        )
        storage.store(
            "/aggregates/daily/2020/04/15/view.csv",
            javaClass.getResourceAsStream("/aggregator/monthly/view/2020-04-15.csv")
        )
        storage.store(
            "/aggregates/daily/2020/04/16/view.csv",
            javaClass.getResourceAsStream("/aggregator/monthly/view/2020-04-16.csv")
        )
        storage.store(
            "/aggregates/daily/2020/04/16/chat.csv",
            javaClass.getResourceAsStream("/aggregator/monthly/view/2020-04-16.csv")
        )
        storage.store(
            "/aggregates/daily/2020/04/16/order.csv",
            javaClass.getResourceAsStream("/aggregator/monthly/view/2020-04-16.csv")
        )

        // WHEN
        getAggregator().aggregate(storage)

        // THEN
        val file = File("$storageDirectory/aggregates/monthly/2020/04/view.csv")
        assertTrue(file.exists())

        assertFileMatches(
            javaClass.getResourceAsStream("/aggregator/monthly/view/output.csv"),
            ByteArrayInputStream(file.readBytes())
        )
    }
}
