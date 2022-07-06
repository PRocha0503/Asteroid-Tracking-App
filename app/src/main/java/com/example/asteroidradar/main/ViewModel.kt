package com.example.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.asteroidradar.database.getDatabase
import com.example.asteroidradar.domain.Asteroid
import com.example.asteroidradar.domain.NetworkAsteroidContainer
import com.example.asteroidradar.domain.asDatabaseModel
import com.example.asteroidradar.repository.DailyImageRepository
import com.example.asteroidradar.utils.Constants
import com.example.asteroidradar.utils.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val dailyImageRepository = DailyImageRepository(database)
    var image = dailyImageRepository.image
    var asteroids = dailyImageRepository.asteroids

    private var _navigateToDetails = MutableLiveData<Asteroid>()
    val navigateToDetails
        get() = _navigateToDetails

    private var _error = MutableLiveData<Boolean>()
    val error
        get() = _error

    init {
        viewModelScope.launch {
            try {
                dailyImageRepository.refreshImage()
                getAsteroids()
            }catch (e:Exception){
                e.printStackTrace()
                error.value = true
            }

        }
    }


    fun getAsteroids(){
        dailyImageRepository.getAsteroidsFromNetwork(getToday()).enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                viewModelScope.launch {
                    dailyImageRepository.insertAsteroidsIntoDB(response.body()?.let { parseAsteroidsJsonResult(JSONObject(it)) }!!)
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                val error = t.message
                _error.value = true
            }
        })
    }

    fun navigateToDetails(asteroid: Asteroid){
        _navigateToDetails.value = asteroid
    }

    fun hasNavegatedToDetails() {
        _navigateToDetails.value = null
    }

    fun hasShownError() {
        error.value = null
    }

    private fun getToday(): String {
        val calendar = Calendar.getInstance()
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(currentTime)
    }


    /**
     * Factory for constructing MainViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}