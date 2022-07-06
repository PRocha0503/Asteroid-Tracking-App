package com.example.asteroidradar.domain

import android.os.Parcelable
import com.example.asteroidradar.database.DatabaseAsteroid
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class DailyImage(val url:String, @Json(name = "media_type") val mediaType: String, val title:String)

@Parcelize
data class Asteroid(val id: Long, val codename: String, val closeApproachDate: String,
                    val absoluteMagnitude: Double, val estimatedDiameter: Double,
                    val relativeVelocity: Double, val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean) : Parcelable

data class NetworkAsteroidContainer(val asteroids:  ArrayList<Asteroid>)

fun NetworkAsteroidContainer.asDatabaseModel():  Array<DatabaseAsteroid> {
    return asteroids.map {
        DatabaseAsteroid (
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
                )
    }.toTypedArray()
}

