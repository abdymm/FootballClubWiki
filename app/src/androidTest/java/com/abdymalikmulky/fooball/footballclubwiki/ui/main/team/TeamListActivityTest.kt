package com.abdymalikmulky.fooball.footballclubwiki.ui.main.team

import android.os.SystemClock
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import com.abdymalikmulky.fooball.footballclubwiki.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TeamListActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(TeamListActivity::class.java)

    @Before
    fun setup() {

    }

    @Test
    fun listEventBehaveTest() {
        SystemClock.sleep(3000)
        onView(withId(R.id.league_spinner)).check(matches(isDisplayed()))
        onView(withId(R.id.league_spinner)).perform(click())
        onView(withText("Spanish La Liga")).perform(click())
        SystemClock.sleep(3000)


        onView(withText("Barcelona")).check(matches(isDisplayed()))
        onView(withText("Barcelona")).perform(click())
    }

}