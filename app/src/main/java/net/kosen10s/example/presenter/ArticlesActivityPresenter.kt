package net.kosen10s.example.presenter

import android.util.Log
import net.kosen10s.example.contracts.ArticlesContract
import net.kosen10s.example.datasource.ArticlesDataSource
import net.kosen10s.example.entity.Article
import net.kosen10s.example.view.screen.articles.ArticlesActivity

class ArticlesActivityPresenter() {

    private var articlesDataSource = ArticlesDataSource()
    var view: ArticlesContract.View? = null

    constructor(articlesDataSource: ArticlesDataSource): this() {
        this.articlesDataSource = articlesDataSource
    }

    fun getArticles() {
        articlesDataSource.getTopHeadlines(onSuccess = {
            view?.onGetArticles(it)
        }, onError = {
            Log.d("getArticles", it?.message)
        })
    }


}