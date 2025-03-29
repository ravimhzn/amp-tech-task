package com.ravimhzn.amp.task

import androidx.annotation.CallSuper
import androidx.compose.ui.test.junit4.createComposeRule
import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import kotlin.reflect.KClass

@RunWith(RobolectricTestRunner::class)
abstract class BaseComposeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @CallSuper
    @Before
    open fun setup() {
        MockitoAnnotations.openMocks(this)
        MockKAnnotations.init(this)
    }

    @CallSuper
    @After
    open fun cleanUp() {
        unmockkAll()
        Mockito.validateMockitoUsage()
    }

    open fun mockkStatic(clz: KClass<*>) {
        io.mockk.mockkStatic(clz)
    }

    open fun mockkObject(obj: Any) {
        io.mockk.mockkObject(obj)
    }

}