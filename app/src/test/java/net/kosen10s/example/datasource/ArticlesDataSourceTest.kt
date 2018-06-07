package net.kosen10s.example.datasource

import android.util.Log
import net.kosen10s.example.TestUtil
import net.kosen10s.example.service.HackerNewsService
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.mockito.Mockito

import org.assertj.core.api.Assertions.assertThat
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.CountDownLatch


class ArticlesDataSourceTest {

    private val mockWebServer = MockWebServer()
    private lateinit var dataSource: ArticlesDataSource
    private lateinit var latch: CountDownLatch

    @Before
    fun setUp() {
        val dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest?): MockResponse {
                if (request == null || request.path == null) {
                    return MockResponse().setResponseCode(400)
                }
                if (request.path.matches("/v2/top-headlines?.*".toRegex())) {
                    return MockResponse().setBody(TestUtil().readJsonFromResources("topHeadlines.json")).setResponseCode(200)
                }
                return MockResponse().setResponseCode(404)
            }
        }
        mockWebServer.setDispatcher(dispatcher)
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
                .baseUrl(mockWebServer.url(""))
                .addConverterFactory(MoshiConverterFactory.create())
                .client(OkHttpClient.Builder().build())
                .build()

        val service = retrofit.create(HackerNewsService::class.java)

        dataSource = ArticlesDataSource(service)

        latch = CountDownLatch(1)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getTopHeadlinesTest() {
        var invoked = false
        dataSource.getTopHeadlines(onSuccess = {
            invoked = true
            assertThat(it)
                    .isNotEmpty()
                    .hasSize(10)
            assertThat(it[0].title).isEqualTo("jonpe960/ufsm")
            latch.countDown()
        }, onError = {
        })
        latch.await()
        assertThat(invoked).isTrue()
    }

}