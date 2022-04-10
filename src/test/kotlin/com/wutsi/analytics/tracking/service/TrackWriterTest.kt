package com.wutsi.analytics.tracking.service

import com.wutsi.analytics.tracking.dto.Track
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream

internal class TrackWriterTest {
    @Test
    fun write() {
        val track = createTrack()
        val out = ByteArrayOutputStream()

        TrackWriter().write(arrayListOf(track), out)
        println(out)

        val expected = """
            "time","tenantid","correlationid","deviceid","accountid","merchantid","productid","page","event","value","ip","long","lat","referer","bot","ua","url","impressions"
            "3333","1","123","sample-device","333","555","1234","SR","pageview","100.0","1.1.2.3","111.0","222.0","https://www.google.ca","false","Mozilla/5.0 (compatible; MSIE 9.0; Windows Phone OS 7.5; Trident/5.0; IEMobile/9.0)","https://www.wutsi.com/read/123/this-is-nice?utm_source=email&utm_campaign=test&utm_medium=email","11|12|13"
        """

        assertEquals(expected.trimIndent(), out.toString().trimIndent())
    }

    private fun createTrack() = Track(
        time = 3333,
        ua = "Mozilla/5.0 (compatible; MSIE 9.0; Windows Phone OS 7.5; Trident/5.0; IEMobile/9.0)",
        correlationId = "123",
        bot = false,
        event = "pageview",
        productId = "1234",
        page = "SR",
        value = 100.0,
        long = 111.0,
        lat = 222.0,
        ip = "1.1.2.3",
        deviceId = "sample-device",
        accountId = "333",
        merchantId = "555",
        referer = "https://www.google.ca",
        url = "https://www.wutsi.com/read/123/this-is-nice?utm_source=email&utm_campaign=test&utm_medium=email",
        impressions = "11|12|13",
        tenantId = "1",
    )
}
