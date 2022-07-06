package com.example.asteroidradar.network

import com.example.asteroidradar.database.DatabaseDailyImage
import com.example.asteroidradar.domain.Asteroid
import com.example.asteroidradar.domain.DailyImage
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkDailyImageContainer(val image: NetworkDailyImage)


@JsonClass(generateAdapter = true)
data class NetworkDailyImage(
    val title: String,
    @Json(name = "media_type") val mediaType: String,
    val url: String, )

/**
 * Convert Network results to database objects
 */
fun NetworkDailyImage.asDomainModel(): DailyImage {
    return DailyImage(
        title = title,
        url = url,
        mediaType = mediaType
    )
}

fun NetworkDailyImage.asDatabaseModel(): DatabaseDailyImage {
        return DatabaseDailyImage (
            title = title,
            mediaType = mediaType,
            url = url,
            )
}



