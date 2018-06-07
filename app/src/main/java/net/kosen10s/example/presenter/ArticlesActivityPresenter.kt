package net.kosen10s.example.presenter

import android.util.Log
import net.kosen10s.example.datasource.ArticlesDataSource
import net.kosen10s.example.entity.Article
import net.kosen10s.example.view.screen.articles.ArticlesActivity

class ArticlesActivityPresenter {

    private val articlesDataSource = ArticlesDataSource()

    fun getArticles(onSuccess: (List<Article>) -> Unit) {
        articlesDataSource.getTopHeadlines(onSuccess = {
            onSuccess.invoke(it)
        }, onError = {
            Log.d("getArticles", it?.message)
        })
    }


}