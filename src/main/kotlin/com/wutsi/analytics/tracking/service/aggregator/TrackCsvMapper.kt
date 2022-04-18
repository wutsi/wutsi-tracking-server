package com.wutsi.analytics.tracking.service.aggregator

import com.wutsi.analytics.tracking.dto.Track
import java.util.Locale

class TrackCsvMapper {
    private val columnIndex: MutableMap<String, Int> = HashMap()

    fun column(col: Array<String>) {
        for (i in col.indices) {
            columnIndex[col[i].lowercase(Locale.getDefault())] = i
        }
    }

    fun map(col: Array<String>): Track =
        Track(
            time = getLong("time", col)!!,
            tenantId = getString("tenantid", col),
            correlationId = getString("correlationid", col),
            deviceId = getString("deviceid", col),
            accountId = getString("accountid", col),
            merchantId = getString("merchantid", col),
            productId = getString("productid", col),
            page = getString("page", col),
            event = getString("event", col),
            value = getDouble("value", col),
            ua = getString("ua", col),
            ip = getString("ip", col),
            long = getDouble("long", col),
            lat = getDouble("lat", col),
            referer = getString("referer", col),
            url = getString("url", col),
            impressions = getString("impressions", col),
            bot = "true".equals(getString("bot", col)),
            deviceType = getString("devicetype", col)
        )

    private fun getString(column: String, col: Array<String>): String? {
        val index = columnIndex[column.lowercase(Locale.getDefault())] ?: return null
        return if (index < col.size) col[index] else null
    }

    private fun getDouble(column: String, col: Array<String>): Double? =
        try {
            getString(column, col)?.toDouble()
        } catch (ex: Exception) {
            null
        }

    private fun getLong(column: String, col: Array<String>): Long? =
        try {
            getString(column, col)?.toLong()
        } catch (ex: Exception) {
            null
        }
}
