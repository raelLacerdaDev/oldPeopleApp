package com.example.elderlyapp.utilities

fun formatDateString(rawDate: String): String {
    return try {
        val parts = rawDate.split("T")
        if (parts.size >= 2) {
            val datePart = parts[0]
            val timePart = parts[1].take(5)

            val dateComponents = datePart.split("-")
            if (dateComponents.size == 3) {
                val (year, month, day) = dateComponents
                "$month/$day/$year at $timePart"
            } else rawDate
        } else rawDate
    } catch (e: Exception) {
        rawDate
    }
}