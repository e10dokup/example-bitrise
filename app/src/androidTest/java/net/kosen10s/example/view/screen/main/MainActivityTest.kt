package net.kosen10s.example.view.screen.main

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import net.kosen10s.example.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun transactionToArticlesActivityTest() {
        Espresso.onView(ViewMatchers.withId(R.id.start_button)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.start_button)).perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.articles_recycler)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}
