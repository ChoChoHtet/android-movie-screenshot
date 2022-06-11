package com.android.screen_capture.ui.home

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.android.screen_capture.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
@MediumTest
class HomeFragmentTest {


    /**
     * if test fail along with this error , make sure you have granted permission
     * "Display pop_up windows while running in the background" or "auto-start" from app permission settings
     * Activity never becomes requested state "[STARTED, RESUMED, CREATED, DESTROYED]"
     */

    @Test
    fun tapOnItem_navigateToHomeDetail() {

        // val  navController:NavController = mockk(relaxed = true)
        val navController = mock(NavController::class.java)
        val scenario: FragmentScenario<HomeFragment> =
            launchFragmentInContainer(themeResId = R.style.Theme_Screen_capture)
        scenario.moveToState(newState = Lifecycle.State.STARTED)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        // when click on item

        runBlocking {
            val recyclerView = Espresso.onView(withId(R.id.rv_movies))
            delay(TimeUnit.SECONDS.toMillis(1))
            recyclerView.perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0, ViewActions.click()
                )
            )
        }

        // THEN - Verify that we navigate to the first detail screen
        /* verify {
             val action =HomeFragmentDirections.actionHomeToDetail()
             action.imdbId = "1"
             navController.navigate(action)
         }*/
        val action = HomeFragmentDirections.actionHomeToDetail()
        action.imdbId = "tt4574334"
        verify(navController).navigate(action)
    }


}