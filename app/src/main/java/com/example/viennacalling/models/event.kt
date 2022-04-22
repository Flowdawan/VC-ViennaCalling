package com.example.viennacalling.models

data class Event(
    val id: String,
    val title: String,
    val date: String,
    val type: String,
    val place: String,
    val price: String,
    val description: String,
    val images: List<String>
)

fun getEvents(): List<Event> {
    return listOf(
        Event(
            id = "486640",
            title = "Strandbar Herrmann 2022",
            date = "1.4. bis 30.9",
            type = "Open-Air",
            place = "Hermannpark",
            price = "free",
            description = "Es geht wieder los: Ab 1. April hat die Strandbar Herrmann wieder geöffnet. Heuer wird uns KrazyKitchen bekochen, es gibt thailändisches Streetfood.",
            images = listOf(
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMjEyOTYyMzUxNl5BMl5BanBnXkFtZTcwNTg0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BNzM2MDk3MTcyMV5BMl5BanBnXkFtZTcwNjg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTY2ODQ3NjMyMl5BMl5BanBnXkFtZTcwODg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTMxOTEwNDcxN15BMl5BanBnXkFtZTcwOTg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTYxMDg1Nzk1MV5BMl5BanBnXkFtZTcwMDk0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg"
            )
        ),
        Event(
            id = "486671",
            title = "90ies Club @ The Loft",
            date = "23.4",
            type = "Indoor",
            place = "Loft",
            price = "10",
            description = "Lust auf die größten Hits des aktuellen Mode-Lieblingsjahrzehnts? Dann geht’s am Samstag, den 23. April, ab ins The Loft, denn dort findet der 90ies Club statt!",
            images = listOf(
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMjEyOTYyMzUxNl5BMl5BanBnXkFtZTcwNTg0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BNzM2MDk3MTcyMV5BMl5BanBnXkFtZTcwNjg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTY2ODQ3NjMyMl5BMl5BanBnXkFtZTcwODg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTMxOTEwNDcxN15BMl5BanBnXkFtZTcwOTg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTYxMDg1Nzk1MV5BMl5BanBnXkFtZTcwMDk0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg"
            )
        ),

        Event(
            id = "486871",
            title = "90ies + 2000s Single Party @ The Loft",
            date = "30.4",
            type = "Indoor",
            place = "Loft",
            price = "10",
            description = "Woran merkt man, dass der Frühling kommt? Richtig, die Schmetterlinge sind im Bauch und nicht auf den Blumenwiesen unterwegs. Daher laden 90ies und 2000s Club zur großen Single Party ins The Loft.",
            images = listOf(
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMjEyOTYyMzUxNl5BMl5BanBnXkFtZTcwNTg0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BNzM2MDk3MTcyMV5BMl5BanBnXkFtZTcwNjg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTY2ODQ3NjMyMl5BMl5BanBnXkFtZTcwODg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTMxOTEwNDcxN15BMl5BanBnXkFtZTcwOTg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTYxMDg1Nzk1MV5BMl5BanBnXkFtZTcwMDk0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg"
            )
        ),
        Event(
            id = "485729",
            title = "Open Floor",
            date = "7.5",
            type = "OpenAir",
            place = "Museumsplatz",
            price = "free",
            description = "Für alle ab 15 Jahren, Leitung: Karin Cheng, Romy Kolb, Tina Rauter. TänzerInnen + Bewegungsmotivierte können sich in einer 2-stündigen Jam-Session kreativ austauschen, begleitet von DJs, die mit House-, HipHop-, Disco-, Funk- und Voguebeats eine Atmosphäre wie im Club schaffen. Davor ab 17 Uhr abwechselnd Slam + Jam und Open Decks! ",
            images = listOf(
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMjEyOTYyMzUxNl5BMl5BanBnXkFtZTcwNTg0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BNzM2MDk3MTcyMV5BMl5BanBnXkFtZTcwNjg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTY2ODQ3NjMyMl5BMl5BanBnXkFtZTcwODg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTMxOTEwNDcxN15BMl5BanBnXkFtZTcwOTg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTYxMDg1Nzk1MV5BMl5BanBnXkFtZTcwMDk0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg"
            )
        ),
        Event(
            id = "487009",
            title = "Wir sind Wien. Festival der Bezirke 2022",
            date = "1.6. bis 23.6",
            type = "Open Air",
            place = "2 Bezirk",
            price = "free",
            description = "Eröffnung am 31.05.2022. Geboten wird ein sehr vielfältiges Angebot an Kunst + Kultur, natürlich wie gewohnt bei freiem Eintritt. Kulturpfad durch alle Bezirke Wiens: am 01.06. im 1. Bezirk / am 02.06. im 2. Bezirk/ usw. Jeden Tag Musik, Tanz, Theater, Kunst und Design, Film u.v.a ",
            images = listOf(
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMjEyOTYyMzUxNl5BMl5BanBnXkFtZTcwNTg0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BNzM2MDk3MTcyMV5BMl5BanBnXkFtZTcwNjg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTY2ODQ3NjMyMl5BMl5BanBnXkFtZTcwODg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTMxOTEwNDcxN15BMl5BanBnXkFtZTcwOTg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTYxMDg1Nzk1MV5BMl5BanBnXkFtZTcwMDk0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg"
            )
        ),
        Event(
            id = "tt0499549",
            title = "39. Donauinselfest 2022",
            date = "24.6. bis 26.6.",
            type = "AFestival",
            place = "Donauinsel",
            price = "free",
            description = "Das Donauinselfest bietet dir auch im Jahr 2022 – bereits zum 39. Mal – eine unterhaltsame Reise. Konzerte, Kabarett, sportliche Attraktionen, kulinarische Schmankerl, extra Programm für Kinder und Information über Arbeitswelt und Politik – auch für dich ist in diesem Jahr bestimmt etwas Interessantes, Neues oder bereits Liebgewonnenes dabei. Drei Tage lang darf ein einzigartiges Programm für alle Wienerinnen und Wiener und BesucherInnen aus aller Welt genossen werden. Einzigartig vor allem deshalb, weil es kostenlos erlebt werden kann. Einzigartig auch deshalb, weil die BesucherInnen in einer Vielfalt feiern, die in Summe ein ganz besonderes Ganzes ergibt.",
            images = listOf(
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMjEyOTYyMzUxNl5BMl5BanBnXkFtZTcwNTg0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BNzM2MDk3MTcyMV5BMl5BanBnXkFtZTcwNjg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTY2ODQ3NjMyMl5BMl5BanBnXkFtZTcwODg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTMxOTEwNDcxN15BMl5BanBnXkFtZTcwOTg0MTUzNA@@._V1_SX1777_CR0,0,1777,999_AL_.jpg",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BMTYxMDg1Nzk1MV5BMl5BanBnXkFtZTcwMDk0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg"
            )
        ))
}
