package fergaral.autoshorten.shorteners.cuttly.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CuttlyService {
    @GET("api.php")
    fun shorten(@Query("key") key: String, @Query("short") longUrl: String): Call<CuttlyResult>
}