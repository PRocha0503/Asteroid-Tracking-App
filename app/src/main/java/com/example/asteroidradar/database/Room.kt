package com.example.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.asteroidradar.domain.DailyImage

@Dao
interface DailyImageDao{

    @Query(value = "select * from DatabaseDailyImage limit 1")
    fun getDailyImage(): LiveData<DatabaseDailyImage>

    @Query(value = "delete from DatabaseDailyImage")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg image:DatabaseDailyImage)

    @Query(value = "select * from databaseasteroid order by closeApproachDate desc")
    fun getAllAsteroids(): LiveData<List<DatabaseAsteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAsteroids(vararg asteroid:DatabaseAsteroid)

    @Query(value = "delete from databaseasteroid")
    fun deleteAllAsteroids()

}


@Database(entities = [DatabaseDailyImage::class,DatabaseAsteroid::class], version = 2)
abstract class DailyImageDatabase : RoomDatabase(){
    abstract val dailyImageDao: DailyImageDao
}

private lateinit var INSTANCE:DailyImageDatabase

fun getDatabase(context: Context): DailyImageDatabase {
    synchronized(DailyImageDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext,DailyImageDatabase::class.java,"image").fallbackToDestructiveMigration().build()
        }
        return INSTANCE
    }
}