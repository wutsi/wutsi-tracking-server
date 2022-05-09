package com.wutsi.analytics.tracking.service.metric

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.analytics.tracking.service.AbstractAggregatorTestBase
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.File
import java.time.LocalDate
import kotlin.test.assertTrue

internal class MetricAggregatorYearlyTest : AbstractAggregatorTestBase() {
    override fun getAggregator() = MetricAggregatorYearly(MetricType.VIEW, LocalDate.of(2020, 4, 14))

    @Test
    fun aggregate() {
        // GIVEN
        storage.store(
            "/aggregates/monthly/2020/04/view.csv",
            javaClass.getResourceAsStream("/aggregator/yearly/view/2020-04.csv")
        )
        storage.store(
            "/aggregates/monthly/2020/05/view.csv",
            javaClass.getResourceAsStream("/aggregator/yearly/view/2020-05.csv")
        )
        storage.store(
            "/aggregates/monthly/2020/06/view.csv",
            javaClass.getResourceAsStream("/aggregator/yearly/view/2020-06.csv")
        )
        storage.store(
            "/aggregates/monthly/2020/04/chat.csv",
            javaClass.getResourceAsStream("/aggregator/yearly/view/2020-06.csv")
        )
        storage.store(
            "/aggregates/monthly/2020/04/order.csv",
            javaClass.getResourceAsStream("/aggregator/yearly/view/2020-06.csv")
        )

        // WHEN
        getAggregator().aggregate(storage)

        // WHEN
        val file = File("$storageDirectory/aggregates/yearly/2020/view.csv")
        assertTrue(file.exists())

        assertFileMatches(
            javaClass.getResourceAsStream("/aggregator/yearly/view/output.csv"), ByteArrayInputStream(file.readBytes())
        )
    }
}
