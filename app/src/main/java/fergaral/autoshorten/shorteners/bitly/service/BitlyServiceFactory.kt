package fergaral.autoshorten.shorteners.bitly.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by Fer on 08/02/2018.
 */
class BitlyServiceFactory {
    companion object {
        val BITLY_API_URL = "https://api-ssl.bitly.com"

        fun getBitlyService(): BitlyService {
            val retrofit = Retrofit.Builder()
                    .baseUrl(BITLY_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create<BitlyService>(BitlyService::class.java)
        }
    }
}