package com.manukanagala.everydaycalculations

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculationTests {

    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun calculate_20_discount(){
        onView(withId(R.id.original_amount_edit_text))
            .perform(typeText("20")).perform(ViewActions.closeSoftKeyboard())

        onView(withId(R.id.tip_percentage_edit_text))
            .perform(typeText("20")).perform(ViewActions.closeSoftKeyboard())

        onView(withId(R.id.calculate)).perform(click())

        onView(withId(R.id.tip_amount)).check(matches(withText(containsString("4"))))

        onView(withId(R.id.final_amount)).check(matches(withText(containsString("16"))))

    }


}