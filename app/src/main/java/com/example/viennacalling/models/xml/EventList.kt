package com.example.viennacalling.models.xml

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


@Root(name = "item", strict = false)
data class EventList @JvmOverloads constructor(

    @field:ElementList(
        required = true,
        name = "title",
        entry = "title",
        inline = true,
        empty = true
    )
    var titleList: List<String>? =
        null,

    @field:Element(name = "link", required = false)
    @param:Element(name = "link", required = false)
    var link: String? = null,

    @field:ElementList(
        required = false,
        name = "description",
        entry = "description",
        inline = true,
        empty = true
    )
    var descriptionList: List<String>? =
        null,

    @field:Element(name = "category", required = false)
    @param:Element(name = "category", required = false)
    var category: String? = null,

    @field:Element(name = "subject", required = false)
    @param:Element(name = "subject", required = false)
    var subject: String? = null,

    @field:Element(name = "dtstart", required = true)
    @param:Element(name = "dtstart", required = true)
    var dtstart: String? = null,

    @field:Element(name = "dtend", required = false)
    @param:Element(name = "dtend", required = false)
    var dtend: String? = null,


    @field:Element(name = "point", required = false)
    @param:Element(name = "point", required = false)
    var point: String? = null,

    @field:Element(name = "location", required = false)
    @param:Element(name = "location", required = false)
    var location: Location? = null,

    @field:Element(name = "content", required = false)
    @param:Element(name = "content", required = false)
    var content: Content? = null
)

@Root(name = "content", strict = false)
data class Content @JvmOverloads constructor(
    @field:Attribute(name = "url", required = false)
    var url: String? = null
)

@Root(name = "location", strict = false)
data class Location @JvmOverloads constructor(

    @field:Element(name = "street-address", required = false)
    @param:Element(name = "street-address", required = false)
    var street_address: String? = null,

    @field:Element(name = "postal-code", required = false)
    @param:Element(name = "postal-code", required = false)
    var postal_code: String? = null,
)