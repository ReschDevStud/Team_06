package com.team06.focuswork

import android.view.Gravity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class OverviewInstrumentedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun overviewCreateTaskTest() {
        onView(withId(R.id.fab))
                .perform(click())
        // Wait short amount of time to ensure everything has loaded
        Thread.sleep(400)

        onView(withId(R.id.containerCreateTask))
                .check(matches(isDisplayed()))

        onView(withId(R.id.taskCreate))
                .check(matches(not(isEnabled())))
    }

    @Test
    fun overviewViewTaskTest() {
        onView(withTagValue(`is`("Task:0" as Any?))).perform(click())

        // Wait short amount of time to ensure everything has loaded
        Thread.sleep(400)

        onView(withId(R.id.title_of_taskdetails))
                .check(matches(isDisplayed()))
    }

    @Test
    fun overviewNavigationTest() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT))) // Left Drawer should be closed.
                .perform(DrawerActions.open());

        // Wait short amount of time to ensure everything has loaded
        Thread.sleep(400)

        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_timer));

        onView(withId(R.id.buttonStartTimer))
                .check(matches(isDisplayed()))
    }
}