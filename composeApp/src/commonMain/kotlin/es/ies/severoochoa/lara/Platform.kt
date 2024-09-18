package es.ies.severoochoa.lara

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform