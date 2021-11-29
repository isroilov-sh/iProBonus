package com.test.iprobonusapi.repository

import androidx.lifecycle.MutableLiveData
import com.test.iprobonusapi.api.NetworkAPI
import com.test.iprobonusapi.api.NetworkFields.CONTENT_TYPE_APPLICATION_JSON
import com.test.iprobonusapi.data.GetBonusResponse
import com.test.iprobonusapi.data.PostAccessTokenResponse
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NetworkRepository {

    val getTokenData = MutableLiveData<PostAccessTokenResponse>()
    val errorMessage = MutableLiveData<String>()
    val getBonusData = MutableLiveData<GetBonusResponse>()

    private val networkAPI: NetworkAPI = NetworkAPI.getInstance()

    fun postAccessToken(accessKey: String, clientId: String, deviceId: String) {

        val mediaType: MediaType? = MediaType.parse(CONTENT_TYPE_APPLICATION_JSON)

        val paramObject = JSONObject()
        paramObject.apply {
            put("idClient", clientId)
            put("paramValue", deviceId)
        }

        val body = RequestBody.create(
            mediaType,
            paramObject.toString()
        )

        networkAPI.postAccessToken(accessKey = accessKey, requestBody = body).enqueue(
            object : Callback<PostAccessTokenResponse> {

                override fun onResponse(
                    call: Call<PostAccessTokenResponse>,
                    response: Response<PostAccessTokenResponse>
                ) {
                    getTokenData.postValue(response.body())
                }

                override fun onFailure(call: Call<PostAccessTokenResponse>, t: Throwable) {
                    errorMessage.postValue(t.message)
                }
            }
        )
    }

    fun getBonusData(accessKey: String, accessToken: String) {
        networkAPI.getBonus(accessKey = accessKey, accessToken = accessToken).enqueue(
            object : Callback<GetBonusResponse> {

                override fun onResponse(
                    call: Call<GetBonusResponse>,
                    response: Response<GetBonusResponse>
                ) {
                    getBonusData.postValue(response.body())
                }

                override fun onFailure(call: Call<GetBonusResponse>, t: Throwable) {
                    errorMessage.postValue(t.message)
                }
            }
        )
    }
}