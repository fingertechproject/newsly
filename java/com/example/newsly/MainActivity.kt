package com.example.newsly


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.bumptech.glide.Glide
import com.example.newsly.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

   private lateinit var binding: ActivityMainBinding
    private lateinit  var newsList : List<Article>
    var index = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getNews()


    }

    private fun getNews() {
        val news = NewsService.newsInstance.getHeadlines("in",1)
        news.enqueue(object:Callback<News>{
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news!=null){
                    Log.d("Khalid",news.toString())
                    newsList = news.articles

                    binding.newsHeadlines.text= newsList[index].title
                    binding.newsDesc.text = newsList[index].content
                    Glide.with(this@MainActivity).load(newsList[index].urlToImage).into(binding.newsImage)
                    binding.shareNews.setOnClickListener {
                        shareNews()
                    }
                    binding.fullNews.setOnClickListener {
                        fullNewsws(newsList[index].url)
                    }
                    binding.nextbtn.setOnClickListener {
                        if (index < newsList.size - 1){
                            index++
                            binding.newsHeadlines.text = newsList[index].title
                            binding.newsDesc.text = newsList[index].description
                            Glide.with(this@MainActivity).load(newsList[index].urlToImage)
                                .into(binding.newsImage)
                        }
                    }
                    binding.prebtn.setOnClickListener {
                        if(index>=1){
                            index--
                            binding.newsHeadlines.text = newsList[index].description
                            binding.newsDesc.text = newsList[index].content
                            Glide.with(this@MainActivity).load(newsList[index].urlToImage)
                                .into(binding.newsImage)
                        }
                    }


                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {

                Log.d("Khalid","error to fetch data",t)
            }

        })
    }

    fun shareNews() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")

        intent.putExtra(Intent.EXTRA_TEXT,newsList[index].url)
        startActivity(Intent.createChooser(intent,"Share this News "))
    }
    fun fullNewsws(url:String) {
       var builder: CustomTabsIntent.Builder =  CustomTabsIntent.Builder();
       val customTabsIntent:CustomTabsIntent  = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
}