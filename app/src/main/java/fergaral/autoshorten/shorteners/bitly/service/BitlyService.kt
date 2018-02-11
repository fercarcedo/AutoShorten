package fergaral.autoshorten.shorteners.bitly.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Fer on 08/02/2018.
 */
interface BitlyService {
    @GET("v3/shorten")
    fun shorten(@Query("access_token") accessToken: String, @Query("longUrl") longUrl: String): Call<BitlyResult>
}