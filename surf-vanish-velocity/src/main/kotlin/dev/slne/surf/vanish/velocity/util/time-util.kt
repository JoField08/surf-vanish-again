package dev.slne.surf.vanish.velocity.util

import dev.slne.surf.vanish.velocity.VelocityMain
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

private val berlinZone = ZoneId.of("Europe/Berlin")
private val germanLocale = Locale.GERMANY
private val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", germanLocale)
private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss", germanLocale)

fun VelocityMain.currentTime(): String {
    return LocalDateTime.now(berlinZone).format(timeFormatter)
}

fun VelocityMain.currentDate(): String {
    return LocalDate.now().format(dateFormatter)
}

fun VelocityMain.currentDay(): String {
    return LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, germanLocale)
}
