package com.wutsi.analytics.tracking.entity

enum class MetricType(val eventType: EventType) {
    VIEW(EventType.VIEW),
    CHAT(EventType.CHAT),
    SHARE(EventType.SHARE),
    ORDER(EventType.ORDER),
    SALE(EventType.ORDER),
}
