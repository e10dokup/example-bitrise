package net.kosen10s.example.contracts

import net.kosen10s.example.entity.Article

interface ArticlesContract {
    interface View {
        fun onGetArticles(articles: List<Article>)
    }
}