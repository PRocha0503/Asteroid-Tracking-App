package com.example.asteroidradar.work

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.asteroidradar.database.getDatabase
import com.example.asteroidradar.domain.NetworkAsteroidContainer
import com.example.asteroidradar.domain.asDatabaseModel
import com.example.asteroidradar.repository.DailyImageRepository
import com.example.asteroidradar.utils.Constants
import com.example.asteroidradar.utils.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

private fun getToday(): String {
    val calendar = Calendar.getInstance()
    val currentTime = calendar.time
    val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(currentTime)
}

class RefreshDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = DailyImageRepository(database)
        return try {
            repository.refreshImage()
            repository.getAsteroidsFromNetwork(getToday()).enqueue(object : retrofit2.Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    database.dailyImageDao.deleteAllAsteroids()
                    database.dailyImageDao.insertAllAsteroids(*NetworkAsteroidContainer(response.body()?.let { parseAsteroidsJsonResult(JSONObject(it)) }!!).asDatabaseModel())
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    val error = t.message
                    Log.i("DebugError",error.toString())
                }
            })
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
    }
