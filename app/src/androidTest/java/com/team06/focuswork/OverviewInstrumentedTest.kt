package com.team06.focuswork

import android.view.Gravity
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class OverviewInstrumentedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    //region UI Utility functions
    private fun setupTaskStrings(taskName: String, taskDescription: String) {
        onView(withId(R.id.taskName))
                .perform(ViewActions.clearText(), ViewActions.typeText(taskName))
        onView(withId(R.id.taskDescription))
                .perform(ViewActions.clearText(), ViewActions.typeText(taskDescription))
        onView(isRoot())
                .perform(ViewActions.closeSoftKeyboard())
    }

    private fun setStartDateValues(cal: Calendar) {
        onView(withId(R.id.taskStartDate))
                .perform(click())
        onView(withClassName(Matchers.equalTo(DatePicker::class.java.name)))
                .perform(PickerActions.setDate(
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)))
        onView(withId(android.R.id.button1)).perform(click())
    }

    private fun setStartTimeValues(cal: Calendar) {
        onView(withId(R.id.taskStartTime))
                .perform(click())
        onView(withClassName(Matchers.equalTo(TimePicker::class.java.name)))
                .perform(PickerActions.setTime(cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE)))
        onView(withId(android.R.id.button1)).perform(click())
    }

    private fun setEndDateValues(cal: Calendar) {
        onView(withId(R.id.taskEndDate))
                .perform(click())
        onView(withClassName(Matchers.equalTo(DatePicker::class.java.name)))
                .perform(PickerActions.setDate(
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)))
        onView(withId(android.R.id.button1)).perform(click())
    }

    private fun setEndTimeValues(cal: Calendar) {

        onView(withId(R.id.taskEndTime))
                .perform(click())
        onView(withClassName(Matchers.equalTo(TimePicker::class.java.name)))
                .perform(PickerActions.setTime(cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE)))
        onView(withId(android.R.id.button1)).perform(click())
    }

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
        onView(withId(R.id.fab))
                .perform(click())
        // Wait short amount of time to ensure everything has loaded
        Thread.sleep(400)
        onView(withId(R.id.containerCreateTask))
                .check(matches(isDisplayed()))

        // At first, Task Create Button should not be enabled
        onView(withId(R.id.taskCreate))
                .check(matches(not(isEnabled())))

        setupTaskStrings("createSimpleTask", "SimpleTaskDescription");
        val startDate = GregorianCalendar(2022, 10, 22, 10, 0)
        val endDate = GregorianCalendar(2022, 10, 22, 11, 0)
        setStartDateValues(startDate)
        setStartTimeValues(startDate)
        setEndDateValues(endDate)
        setEndTimeValues(endDate)

        // Task Create Button should now be enabled
        onView(withId(R.id.taskCreate))
                .check(matches(isEnabled()))
                .perform(click())

        // After click, overview should be shown again
        onView(withId(R.id.fragment_container_overview))
                .check(matches(isDisplayed()))

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