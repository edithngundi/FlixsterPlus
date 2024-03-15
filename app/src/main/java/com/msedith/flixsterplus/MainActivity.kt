package com.msedith.flixsterplus

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.recyclerview.widget.LinearLayoutManager

class MainActivity : AppCompatActivity() {

    private lateinit var service: MovieApiService
    private lateinit var rvMovies: RecyclerView
    private lateinit var moviesAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "FlixsterPlus"

        rvMovies = findViewById(R.id.rvMovies)
        rvMovies.layoutManager = LinearLayoutManager(this)

        moviesAdapter = MovieAdapter(emptyList())
        rvMovies.adapter = moviesAdapter

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(MovieApiService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val movieResponse = service.getNowPlayingMovies("a07e22bc18f5cb106bfe4cc1f83ad8ed")
                withContext(Dispatchers.Main) {
                    moviesAdapter.updateMovies(movieResponse.results)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val newSpanCount = if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            3
        } else {
            2
        }
        (rvMovies.layoutManager as? GridLayoutManager)?.spanCount = newSpanCount
        rvMovies.layoutManager?.requestLayout()
    }

}
