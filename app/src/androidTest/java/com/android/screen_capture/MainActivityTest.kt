package com.android.screen_capture

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragment
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import com.android.screen_capture.ui.screenshot.ScreenShotFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class MainActivityTest {


    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)


    @Test
    fun takeScreenshot_navigateToScreenshotView(){
        runBlocking {
            delay(500)
            Espresso.onView(withId(R.id.fab)).perform(ViewActions.click())
            delay(5000)

           val scenario = launchFragment<ScreenShotFragment>(bundleOf("image" to "/storage/emulated/0/Pictures/Capture_1654936188848.jpeg"))
            scenario.onFragment{fragment->
                Assert.assertNotNull(fragment.dialog)
                Assert.assertTrue(fragment.requireDialog().isShowing)
            }
        }
    }
}