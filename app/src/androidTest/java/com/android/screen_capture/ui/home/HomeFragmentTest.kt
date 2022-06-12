package com.android.screen_capture.ui.home

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingPolicies
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.android.screen_capture.R
import com.android.screen_capture.hasItemCount
import com.android.screen_capture.waitUntil
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.Before
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
    private lateinit var scenario: FragmentScenario<HomeFragment>
    private lateinit var navController: NavController

    @Before
    fun setup() {
        // GIVEN - On the home screen

        scenario = launchFragmentInContainer(themeResId = R.style.Theme_Screen_capture)
        scenario.moveToState(newState = Lifecycle.State.STARTED)
        navController = mock(NavController::class.java)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
    }

    @Test
    fun tapOnItem_navigateToHomeDetail() {

        // WHEN -  Click on first item == position 0
        runBlocking {
            val recyclerView = Espresso.onView(withId(R.id.rv_movies))
            // delay(TimeUnit.SECONDS.toMillis(1))
            // Make sure Espresso does not time out
            IdlingPolicies.setMasterPolicyTimeout(2, TimeUnit.MINUTES)
            IdlingPolicies.setIdlingResourceTimeout(2, TimeUnit.MINUTES)
            recyclerView.perform(
                waitUntil(hasItemCount(Matchers.greaterThan(0))),
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0, ViewActions.click()
                )
            )
        }

        // THEN - Verify that it navigate to home detail screen
        val action = HomeFragmentDirections.actionHomeToDetail()
        action.imdbId = "tt4574334"
        verify(navController).navigate(action)
    }

    @Test
    fun scrollToBottom_itemCountGreaterThan10() {

        // WHEN -  Scroll to the end of the screen
        runBlocking {
            val recyclerView = Espresso.onView(withId(R.id.rv_movies))
            recyclerView.perform(
                waitUntil(hasItemCount(Matchers.greaterThan(0))),
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(9)
            )
            delay(TimeUnit.SECONDS.toMillis(4))

            recyclerView.perform(
                waitUntil(hasItemCount(Matchers.greaterThan(10))),
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(9)
            )
        }

        // Verify - Movies lis count has more than 10
        Espresso.onView(withId(R.id.rv_movies)).check(
            ViewAssertions.matches(hasItemCount(Matchers.greaterThan(10)))
        )
    }


}