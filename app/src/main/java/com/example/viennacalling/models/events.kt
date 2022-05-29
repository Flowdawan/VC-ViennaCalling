package com.example.viennacalling.models

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
class EventListResponse @JvmOverloads constructor(
    @field: Element(name = "channel")
    var channel: EventListChannel? = null
)

@Root(name = "channel", strict = false)
class EventListChannel @JvmOverloads constructor(
    @field: ElementList(inline = true)
    var itemList: List<EventItem>? = null
)

@Root(name = "item", strict = false)
data class EventItem @JvmOverloads constructor(
    @field: Element(name = "title")
    var title: String = "",
    @field: Element(name = "link")
    var link: String = "",
    @field: Element(name = "description", required = false)
    var description: String = ""
)