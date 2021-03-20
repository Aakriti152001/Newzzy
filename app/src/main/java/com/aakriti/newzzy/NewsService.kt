package com.aakriti.newzzy

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//http://newsapi.org/v2/top-headlines?country=in&apiKey=7811377d4ea4416ea98d58ce04db58a4
//http://newsapi.org/v2/everything?q=bitcoin&from=2020-11-03&sortBy=publishedAt&apiKey=7811377d4ea4416ea98d58ce04db58a4
const val BASE_URL="https://newsapi.org/"
const val API_KEY="7811377d4ea4416ea98d58ce04db58a4"
interface NewsInterface {
    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadlines(@Query("country")country:String,@Query("page")page:Int):Call<News>

    //http://newsapi.org/v2/top-headlines?apiKey=7811377d4ea4416ea98d58ce04db58a4&country=in&page=1
}
object NewsService{
     val newsInstance:NewsInterface
    init{
        val retrofit=Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        newsInstance=retrofit.create(NewsInterface::class.java)
    }

}