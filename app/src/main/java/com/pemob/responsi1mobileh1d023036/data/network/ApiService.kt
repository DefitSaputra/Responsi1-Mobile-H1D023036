package com.pemob.responsi1mobileh1d023036.data.network

import com.pemob.responsi1mobileh1d023036.data.model.TeamResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    @Headers("X-Auth-Token: 75ac6a0496f04580ad6c45e548b8ebad")
    @GET("v4/teams/522")
    suspend fun getTeamDetail(): Response<TeamResponse>
}