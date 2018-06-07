package net.kosen10s.example.view.screen.articles

import android.support.test.espresso.Espresso
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.IdlingResource
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import net.kosen10s.example.R
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ArticlesActivityTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(ArticlesActivity::class.java)

    var initializeIdlingResource : IdlingResource? = null

    @Before
    fun setUp() {
        initializeIdlingResource = activityRule.activity.loadingIdlingResource
        IdlingRegistry.getInstance().register(initializeIdlingResource)
    }

    @After
    fun tearDown() {
        initializeIdlingResource?.run {
            IdlingRegistry.getInstance().unregister(this)
            initializeIdlingResource = null
        }
    }

    @Test
    fun loadArticlesTest() {
        Espresso.onView(ViewMatchers.withId(R.id.articles_recycler)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.articles_recycler)).check { view, _ ->
            val recyclerView = view as RecyclerView
            assertThat(recyclerView.childCount).isGreaterThan(1)
            assertThat(recyclerView.adapter.itemCount).isGreaterThan(1)
        }
    }
}