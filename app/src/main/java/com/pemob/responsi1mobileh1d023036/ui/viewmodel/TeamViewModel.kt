package com.pemob.responsi1mobileh1d023036.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pemob.responsi1mobileh1d023036.data.model.TeamResponse
import com.pemob.responsi1mobileh1d023036.data.repository.TeamRepository
import kotlinx.coroutines.launch

class TeamViewModel : ViewModel() {

    private val repository = TeamRepository()

    private val _teamData = MutableLiveData<TeamResponse>()
    val teamData: LiveData<TeamResponse> = _teamData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    // Fungsi untuk memanggil API
    fun fetchTeamData() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val response = repository.getTeamDetail()
                if (response.isSuccessful) {
                    _teamData.postValue(response.body())
                } else {
                    _errorMessage.postValue("Gagal memuat data: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Terjadi kesalahan: ${e.message}")
            }
            _isLoading.postValue(false)
        }
    }
}