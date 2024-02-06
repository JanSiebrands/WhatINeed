package de.siebrands.whatineed

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform