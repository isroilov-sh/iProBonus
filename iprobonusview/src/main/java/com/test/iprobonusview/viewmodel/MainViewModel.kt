package com.test.iprobonusview.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.iprobonusapi.data.GetBonusResponse
import com.test.iprobonusapi.data.PostAccessTokenResponse
import com.test.iprobonusapi.repository.NetworkRepository

class MainViewModel constructor(private val repository: NetworkRepository) : ViewModel() {

    var getTokenData = MutableLiveData<PostAccessTokenResponse>()
    var errorMessage = MutableLiveData<String>()
    var getBonusData = MutableLiveData<GetBonusResponse>()

    init {
        getTokenData = repository.getTokenData
        errorMessage = repository.errorMessage
        getBonusData = repository.getBonusData
    }

    fun postAccessToken(
        accessKey: String,
        clientId: String,
        deviceId: String
    ) {
        repository.postAccessToken(accessKey, clientId, deviceId)
    }

    fun getBonusData(accessKey: String, accessToken: String) {
        repository.getBonusData(accessKey, accessToken)
    }
}