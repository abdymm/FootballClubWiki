package com.abdymalikmulky.fooball.footballclubwiki

import android.os.SystemClock
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.abdymalikmulky.fooball.footballclubwiki.R.id.navigation
import com.abdymalikmulky.fooball.footballclubwiki.R.id.navigation_fav_match
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.MainActivity
import com.abdymalikmulky.fooball.footballclubwiki.ui.main.match.FixtureFragment
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.Before
import org.junit.Test
import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.Espresso.onView



@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    val FAVE_TEAM = "Barcelona"

    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        activityRule.activity.openFragment(FixtureFragment.newInstance(true))
    }

    @Test
    fun changeLeagueBehaveTest() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext())
        onView(withText(R.string.menu_team)).perform(click())

        SystemClock.sleep(3000)
        onView(withId(R.id.league_spinner)).check(matches(isDisplayed()))
        onView(withId(R.id.league_spinner)).perform(click())
            onView(withText("Spanish La Liga")).perform(click())
        SystemClock.sleep(3000)


        onView(withText(FAVE_TEAM)).check(matches(isDisplayed()))
        onView(withText(FAVE_TEAM)).perform(click())
    }

    @Test
    fun listEventBehaveTest() {
        var menuFavToggleId = R.id.menu_fav

        SystemClock.sleep(5000)
        onView(withId(R.id.event_list)).check(matches(isDisplayed()))
        onView(withId(R.id.event_list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(R.id.event_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))

        try {
            onView(withId(menuFavToggleId)).check(matches(isDisplayed()))

        } catch (e: NoMatchingViewException) {
            menuFavToggleId = R.id.menu_unfav

            onView(withId(menuFavToggleId)).check(matches(isDisplayed()))
        }


        onView(withId(menuFavToggleId)).perform(click())
        pressBack()


        onView(withId(navigation)).check(matches(isDisplayed()))
        onView(withId(navigation_fav_match)).perform(click())
        SystemClock.sleep(3000)
    }


}