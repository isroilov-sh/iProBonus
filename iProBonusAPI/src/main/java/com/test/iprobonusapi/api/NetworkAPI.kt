package com.test.iprobonusapi.api

import com.test.iprobonusapi.BuildConfig
import com.test.iprobonusapi.api.NetworkConstants.GET_BONUS
import com.test.iprobonusapi.api.NetworkConstants.POST_ACCESS_TOKEN
import com.test.iprobonusapi.api.NetworkFields.ACCESS_KEY
import com.test.iprobonusapi.api.NetworkFields.CONTENT_TYPE
import com.test.iprobonusapi.api.NetworkFields.CONTENT_TYPE_APPLICATION_JSON
import com.test.iprobonusapi.data.GetBonusResponse
import com.test.iprobonusapi.data.PostAccessTokenResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface NetworkAPI {

    @POST(POST_ACCESS_TOKEN)
    fun postAccessToken(
        @Header(ACCESS_KEY) accessKey: String,
        @Header(CONTENT_TYPE) contentType: String = CONTENT_TYPE_APPLICATION_JSON,
        @Body requestBody: RequestBody
    ): Call<PostAccessTokenResponse>


    @GET(GET_BONUS)
    fun getBonus(
        @Header(ACCESS_KEY) accessKey: String,
        @Header(CONTENT_TYPE) contentType: String = CONTENT_TYPE_APPLICATION_JSON,
        @Path("accessToken") accessToken: String
    ): Call<GetBonusResponse>

    companion object {
        private var retrofitService: NetworkAPI? = null

        fun getInstance(): NetworkAPI {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(NetworkAPI::class.java)
            }
            return retrofitService!!
        }
    }
}
