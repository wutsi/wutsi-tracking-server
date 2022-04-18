package com.wutsi.analytics.tracking.service.aggregator

import com.wutsi.analytics.tracking.dto.Track

class TrackCsvMapper : AbstractCsvMapper<Track>() {
    override fun map(col: Array<String>): Track =
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
}
