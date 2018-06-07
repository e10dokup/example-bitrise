package net.kosen10s.example.view.screen.articles

import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_articles.*
import net.kosen10s.example.R
import net.kosen10s.example.common.SimpleIdlingResource
import net.kosen10s.example.contracts.ArticlesContract
import net.kosen10s.example.entity.Article
import net.kosen10s.example.presenter.ArticlesActivityPresenter
import net.kosen10s.example.view.item.ArticleItem

class ArticlesActivity: AppCompatActivity(), ArticlesContract.View {

    private val presenter by lazy {
        ArticlesActivityPresenter()
    }

    private val adapter = GroupAdapter<ViewHolder>().apply {
        setOnItemClickListener({ item, _ ->
            val articleItem = item as? ArticleItem ?: return@setOnItemClickListener
            Snackbar.make(articles_recycler, articleItem.article.title, Snackbar.LENGTH_SHORT).show()
        })
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val loadingIdlingResource : SimpleIdlingResource = SimpleIdlingResource("IdlingResourceViewModelLoading")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_articles)
        articles_recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        articles_recycler.adapter = adapter

        presenter.view = this
    }

    override fun onResume() {
        super.onResume()
        loadingIdlingResource.isIdle = false
        presenter.getArticles()
    }

    override fun onGetArticles(articles: List<Article>) {
        articles.forEach {
            adapter.add(ArticleItem(it))
        }
        loadingIdlingResource.isIdle = true
    }
}