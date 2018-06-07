package net.kosen10s.example.presenter

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import net.kosen10s.example.contracts.ArticlesContract
import net.kosen10s.example.datasource.ArticlesDataSource
import net.kosen10s.example.entity.Article
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer

class ArticlesActivityPresenterTest {

    private lateinit var presenter: ArticlesActivityPresenter
    private lateinit var view: ArticlesContract.View
    private lateinit var dataSource: ArticlesDataSource

    private val callbackCaptor = argumentCaptor<(List<Article>) -> Unit>()
    private val errorCaptor = argumentCaptor<(Throwable?) -> Unit>()

    @Before
    fun setUp() {
        view = Mockito.mock(ArticlesContract.View::class.java)
        dataSource = Mockito.mock(ArticlesDataSource::class.java)
        presenter = ArticlesActivityPresenter(dataSource)
        presenter.view = view
    }

    @Test
    fun getArticlesTest() {
        presenter.getArticles()
        verify(dataSource).getTopHeadlines(callbackCaptor.capture(), errorCaptor.capture())
        callbackCaptor.firstValue.invoke(createList())

        verify(view).onGetArticles(createList())
    }

    private fun createList(): List<Article> {
        val list = ArrayList<Article>()
        list.add(Article("authorA", "titleA", "descriptionA", "urlA", "imageA", "publishedAtA"))
        list.add(Article("authorB", "titleB", "descriptionB", "urlB", "imageA", "publishedAtA"))
        list.add(Article("authorC", "titleC", "descriptionC", "urlC", "imageA", "publishedAtA"))
        return list
    }
}