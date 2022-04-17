package com.example.newsly

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

//https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=a633d3b17ab64f5fb4fc9d3bc5e26f69
//https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=a633d3b17ab64f5fb4fc9d3bc5e26f69
const val API_KEY = "a633d3b17ab64f5fb4fc9d3bc5e26f69"
const val BASE_URL = "https://newsapi.org/v2/"
interface NewsInterface{

    @GET("top-headlines?apiKey=$API_KEY")
    fun getHeadlines(
        @Query("country")country:String,
        @Query("page")page:Int
    ):Call<News>
    //https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=$API_KEY&cocountry=in&page=1
}
object NewsService{
    var newsInstance:NewsInterface

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance = retrofit.create(NewsInterface::class.java)

    }
}