package com.aakriti.newzzy

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.littlemango.stacklayoutmanager.StackLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var adapter:NewsAdapter
    private var articles= mutableListOf<Article>()
    var pageNum=1
    var totalResults=-1
    val TAG="MainActivity"
    private lateinit var mInterstitialAd: InterstitialAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //admob code

        MobileAds.initialize(this)
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mInterstitialAd.adListener = object: AdListener() {
            override fun onAdClosed() {
                super.onAdClosed()
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }

        adapter= NewsAdapter(this@MainActivity,articles)
        var rvNewsList:RecyclerView=findViewById(R.id.rvNewsList)
        var container:ConstraintLayout=findViewById(R.id.container)
        rvNewsList.adapter=adapter
      //  rvNewsList.layoutManager=LinearLayoutManager(this@MainActivity)
        val layoutManager=StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
        layoutManager.setPagerMode(true)
        layoutManager.setPagerFlingVelocity(3000)

        layoutManager.setItemChangedListener(object:StackLayoutManager.ItemChangedListener{
            override fun onItemChanged(position: Int) {
                container.setBackgroundColor(Color.parseColor(ColorPicker.getColor()))
                Log.d(TAG,"First Visible item- ${layoutManager.getFirstVisibleItemPosition()}")
                Log.d(TAG,"Total count- ${layoutManager.itemCount}")
                if(totalResults>layoutManager.itemCount && layoutManager.getFirstVisibleItemPosition()>=layoutManager.itemCount-5)
                {
                    pageNum++
                    getNews()
                }
                if(position%5==0){
                    if (mInterstitialAd.isLoaded) {
                        mInterstitialAd.show()
                    }
                }

            }
        })
        rvNewsList.layoutManager=layoutManager
        getNews()
    }

    private fun getNews() {
        Log.d(TAG,"Request sent for $pageNum")
        val news=NewsService.newsInstance.getHeadlines("in",pageNum)
        news.enqueue(object :Callback<News>{
            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("Aakriti","Error Fetching data",t)
            }

            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news=response.body()
                if(news!=null)
                {
                    totalResults=news.totalResults
                    articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }
}
