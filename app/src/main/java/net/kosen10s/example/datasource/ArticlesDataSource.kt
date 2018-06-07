package net.kosen10s.example.datasource

import io.reactivex.internal.operators.single.SingleDoOnSuccess
import net.kosen10s.example.entity.Article
import net.kosen10s.example.entity.TopHeadlines
import net.kosen10s.example.service.HackerNewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ArticlesDataSource() {

    companion object {
        private var clientInstance: HackerNewsService? = null

        private fun getClientInstance(): HackerNewsService {
            if (clientInstance == null) {
                val retrofit = Retrofit.Builder()
                        .baseUrl("https://newsapi.org")
                        .addConverterFactory(MoshiConverterFactory.create())
                        .build()
                clientInstance = retrofit.create(HackerNewsService::class.java)
            }
            return clientInstance!!
        }
    }

    private var service = getClientInstance()

    constructor(service: HackerNewsService) : this() {
        this.service = service
    }

    fun getTopHeadlines(onSuccess: (List<Article>) -> Unit, onError: (Throwable?) -> Unit) {
        val call = service.getTopHeadlines("hacker-news", "c9aef6a9a28744078d0f7a051523d549")
        call.enqueue(object : Callback<TopHeadlines> {
            override fun onFailure(call: Call<TopHeadlines>?, t: Throwable?) {
                onError.invoke(t)
            }

            override fun onResponse(call: Call<TopHeadlines>?, response: Response<TopHeadlines>?) {
                if (response != null) {
                    val topHeadlines = response.body()
                    if (topHeadlines != null) {
                        onSuccess.invoke(topHeadlines.articles)
                        return
                    }
                }
                onError.invoke(Throwable("Failed response parsing"))
            }
        })
    }


}