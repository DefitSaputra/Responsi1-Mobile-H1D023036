package com.pemob.responsi1mobileh1d023036.data.repository

import com.pemob.responsi1mobileh1d023036.data.model.TeamResponse
import com.pemob.responsi1mobileh1d023036.data.network.RetrofitInstance
import retrofit2.Response

class TeamRepository {
    suspend fun getTeamDetail(): Response<TeamResponse> {
        return RetrofitInstance.api.getTeamDetail()
    }
}