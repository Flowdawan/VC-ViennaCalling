package com.example.viennacalling.models.xml

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel", strict = false)
data class Channel @JvmOverloads constructor(

    @field:ElementList(
        required = false,
        name = "item",
        entry = "item",
        inline = true,
        empty = true
    )
    var eventList: MutableList<EventList>? =
        null
)
