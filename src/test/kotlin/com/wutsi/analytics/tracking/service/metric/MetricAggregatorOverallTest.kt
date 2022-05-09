package com.wutsi.analytics.tracking.service.metric

import com.wutsi.analytics.tracking.entity.MetricType
import com.wutsi.analytics.tracking.service.AbstractAggregatorTestBase
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.File
import java.time.LocalDate
import kotlin.test.assertTrue

internal class MetricAggregatorOverallTest : AbstractAggregatorTestBase() {
    override fun getAggregator() = MetricAggregatorOverall(MetricType.VIEW, LocalDate.of(2020, 4, 14))

    @Test
    fun aggregate() {
        // GIVEN
        storage.store(
            "/aggregates/yearly/2018/view.csv",
            javaClass.getResourceAsStream("/aggregator/overall/view/2018.csv")
        )
        storage.store(
            "/aggregates/yearly/2019/view.csv",
            javaClass.getResourceAsStream("/aggregator/overall/view/2019.csv")
        )
        storage.store(
            "/aggregates/yearly/2020/view.csv",
            javaClass.getResourceAsStream("/aggregator/overall/view/2020.csv")
        )
        storage.store(
            "/aggregates/yearly/2020/chat.csv",
            javaClass.getResourceAsStream("/aggregator/overall/view/2020.csv")
        )
        storage.store(
            "/aggregates/yearly/2020/order.csv",
            javaClass.getResourceAsStream("/aggregator/overall/view/2020.csv")
        )

        // WHEN
        getAggregator().aggregate(storage)

        // WHEN
        val file = File("$storageDirectory/aggregates/overall/view.csv")
        assertTrue(file.exists())

        assertFileMatches(
            javaClass.getResourceAsStream("/aggregator/overall/view/output.csv"), ByteArrayInputStream(file.readBytes())
        )
    }
}
