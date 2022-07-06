package com.example.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.asteroidradar.domain.Asteroid
import com.example.asteroidradar.domain.DailyImage
import com.squareup.moshi.Json

@Entity
data class  DatabaseDailyImage constructor(
    @PrimaryKey
    val url:String,
    val mediaType: String,
    val title:String
)

fun DatabaseDailyImage.asDomainModel(): DailyImage {
    return DailyImage (
            url = url,
            title = title,
            mediaType = mediaType)
}

@Entity
data class  DatabaseAsteroid constructor(
    @PrimaryKey
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid (
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous,
            )
    }
}