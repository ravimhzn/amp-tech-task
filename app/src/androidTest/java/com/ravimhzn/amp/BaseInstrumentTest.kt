package com.ravimhzn.amp

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseInstrumentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

}