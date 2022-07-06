package com.example.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.asteroidradar.database.DailyImageDatabase
import com.example.asteroidradar.database.DatabaseDailyImage
import com.example.asteroidradar.database.asDomainModel
import com.example.asteroidradar.domain.Asteroid
import com.example.asteroidradar.domain.DailyImage
import com.example.asteroidradar.domain.NetworkAsteroidContainer
import com.example.asteroidradar.domain.asDatabaseModel
import com.example.asteroidradar.network.Network
import com.example.asteroidradar.network.asDatabaseModel
import com.example.asteroidradar.utils.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class DailyImageRepository(private val database:DailyImageDatabase) {
    val image:LiveData<DailyImage> =  Transformations.map( database.dailyImageDao.getDailyImage()){
        it?.asDomainModel()
    }
    val asteroids:LiveData<List<Asteroid>> = Transformations.map( database.dailyImageDao.getAllAsteroids()){
        it?.asDomainModel()
    }



    private suspend fun getDailyImage(){
        withContext(Dispatchers.IO) {
            try{
                val image = Network.asteroid.getImage().await()
                //Only Download today image if it is not a video
                if(image.mediaType == "image"){
                    database.dailyImageDao.deleteAll()
                    database.dailyImageDao.insertAll(image.asDatabaseModel())
                }
            }catch(e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun getAsteroidsFromNetwork(today:String): Call<String> {
            return Network.asteroid.getAsteroids(today)
    }
    suspend fun insertAsteroidsIntoDB(data1: ArrayList<Asteroid>) {
        withContext(Dispatchers.IO) {
            database.dailyImageDao.deleteAllAsteroids()
            database.dailyImageDao.insertAllAsteroids(*NetworkAsteroidContainer(data1).asDatabaseModel())
        }
    }


    suspend fun refreshImage(){
        getDailyImage()
    }
}