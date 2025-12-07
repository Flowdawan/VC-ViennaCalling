package at.deflow.viennacalling.mapping

import at.deflow.viennacalling.models.Event
import at.deflow.viennacalling.retrofit.ApiEvent
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun ApiEvent.toEvent(): Event {
    val eventImagesListDefault = listOf(
        "https://cdn.pixabay.com/photo/2016/11/29/06/17/audience-1867754_1280.jpg",
        "https://cdn.pixabay.com/photo/2015/11/22/19/04/crowd-1056764_1280.jpg",
        "https://cdn.pixabay.com/photo/2019/10/15/03/16/black-and-white-4550471_1280.jpg",
        "https://cdn.pixabay.com/photo/2017/03/25/09/51/party-2173187_1280.jpg",
        "https://cdn.pixabay.com/photo/2016/11/23/15/48/audience-1853662_1280.jpg",
        "https://cdn.pixabay.com/photo/2014/07/09/12/17/live-concert-388160_1280.jpg",
        "https://cdn.pixabay.com/photo/2016/11/18/17/47/iphone-1836071_1280.jpg",
        "https://cdn.pixabay.com/photo/2016/11/22/19/15/hand-1850120_1280.jpg",
        "https://cdn.pixabay.com/photo/2016/11/22/19/15/audience-1850119_1280.jpg",
        "https://cdn.pixabay.com/photo/2015/03/08/17/25/musician-664432_1280.jpg",
        "https://cdn.pixabay.com/photo/2016/11/22/18/56/audience-1850022_1280.jpg",
        "https://cdn.pixabay.com/photo/2019/06/11/16/14/vienna-4267377_960_720.jpg",
        "https://cdn.pixabay.com/photo/2019/08/13/17/17/vienna-state-opera-4403839_960_720.jpg",
        "https://cdn.pixabay.com/photo/2018/12/17/14/20/vienna-3880488_960_720.jpg",
    )

    val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN)
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.GERMAN)
    val fallbackFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ", Locale.GERMAN)

    val startDate = this.startTime
        ?.takeIf { it.isNotBlank() }
        ?.let { value ->
            runCatching { LocalDate.parse(value, inputFormatter).format(outputFormatter) }
                .getOrElse { runCatching { LocalDate.parse(value, fallbackFormatter).format(outputFormatter) }.getOrDefault("") }
        } ?: ""
    val endDate = this.endTime
        ?.takeIf { it.isNotBlank() }
        ?.let { value ->
            runCatching { LocalDate.parse(value, inputFormatter).format(outputFormatter) }
                .getOrElse { runCatching { LocalDate.parse(value, fallbackFormatter).format(outputFormatter) }.getOrDefault("") }
        } ?: ""

    return Event(
        id = this.link ?: "", // Use link as a stable, unique ID
        title = this.title ?: "Es ist leider kein Titel vorhanden",
        description = this.description ?: "Es ist leider keine Beschreibung vorhanden",
        category = this.category ?: "",
        link = this.link ?: "Es ist leider kein Link vorhanden",
        url = this.url ?: "",
        subject = this.subject ?: "",
        startTime = startDate,
        endTime = endDate,
        startHour = this.startHour ?: "",
        startMin = this.startMin ?: "",
        point = this.point ?: "",
        streetAddress = this.streetAddress ?: "",
        plz = this.plz ?: "",
        images = if (!this.images.isNullOrBlank()) this.images
        else
            when (this.category) {
                "party" -> eventImagesListDefault.random()
                "sightseeing" -> eventImagesListDefault.random()
                "culture" -> eventImagesListDefault.random()
                else -> eventImagesListDefault.random()
            }
    )
}

fun List<ApiEvent>.toEventList(): List<Event> {
    return this.mapNotNull { runCatching { it.toEvent() }.getOrNull() }
}
