package com.msedith.flixsterplus

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key") apiKey: String): MovieResponse
}
