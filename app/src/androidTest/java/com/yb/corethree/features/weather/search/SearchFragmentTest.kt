package com.yb.corethree.features.weather.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollTo
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.yb.corethree.R
import com.yb.corethree.common.SimpleIdlingResource
import com.yb.corethree.features.main.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class SearchFragmentTest {

    @Rule @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java, true, false)
    @Rule @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()
    private val idlingResource = SimpleIdlingResource

    @Before
    fun setUpTest() {
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun cleanUpTest() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun onStartCheckIfUiIsDisplayed() {
        activityTestRule.launchActivity(null)
        onView(withId(R.id.et_city_name)).check(matches(isDisplayed()))
        onView(withId(R.id.til_city_name)).check(matches(isDisplayed()))
        onView(withId(R.id.srl_cities)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_cities)).check(matches(isDisplayed()))
    }

    @Test
    fun onStartCheckIfSearchToolbarTitleIsDisplayed() {
        activityTestRule.launchActivity(null)
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        onView(withText(context.getString(R.string.search_fragment_title))).check(matches(isDisplayed()))
    }

}