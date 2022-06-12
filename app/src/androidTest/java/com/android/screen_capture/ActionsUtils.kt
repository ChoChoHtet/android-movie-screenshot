package com.android.screen_capture

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.IdlingResource.ResourceCallback
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.actionWithAssertions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.StringDescription

val appName = hashMapOf(
    "debug" to "app-debug.apk",
    "release" to "app-release.apk"
)
fun hasItemCount(matcher: Matcher<Int>): Matcher<View?> {
    return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has item count: ")
            matcher.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            return matcher.matches(view.adapter!!.itemCount)
        }
    }
}

fun waitUntil(matcher: Matcher<View?>): ViewAction? {
    return actionWithAssertions(object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.isAssignableFrom(View::class.java)
        }

        override fun getDescription(): String {
            val description = StringDescription()
            matcher.describeTo(description)
            return String.format("wait until: %s", description)
        }

        override fun perform(uiController: UiController, view: View) {
            if (!matcher.matches(view)) {
                val callback = LayoutChangeCallback(matcher)
                try {
                    IdlingRegistry.getInstance().register(callback)
                    view.addOnLayoutChangeListener(callback)
                    uiController.loopMainThreadUntilIdle()
                } finally {
                    view.removeOnLayoutChangeListener(callback)
                    IdlingRegistry.getInstance().unregister(callback)
                }
            }
        }
    })
}

class LayoutChangeCallback(private val matcher: Matcher<View?>) :IdlingResource,View.OnLayoutChangeListener{
    private var matched = false
    private  var callback : ResourceCallback? =null
    override fun getName(): String {
        return "Layout change callback";
    }

    override fun isIdleNow(): Boolean {
       return  matched
    }

    override fun registerIdleTransitionCallback(callback: ResourceCallback?) {
        this.callback = callback
    }

    override fun onLayoutChange(
        view: View?,
        p1: Int,
        p2: Int,
        p3: Int,
        p4: Int,
        p5: Int,
        p6: Int,
        p7: Int,
        p8: Int
    ) {
        matched = matcher.matches(view)
        callback?.onTransitionToIdle()
    }

}