package fergaral.autoshorten.shorteners.cuttly.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CuttlyServiceFactory {
    companion object {
        val CUTTLY_API_URL = "https://cutt.ly/api/"

        fun getCuttlyService(): CuttlyService {
            val retrofit = Retrofit.Builder()
                    .baseUrl(CUTTLY_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create<CuttlyService>(CuttlyService::class.java)
        }
    }
}