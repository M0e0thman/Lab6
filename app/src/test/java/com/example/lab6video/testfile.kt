package com.example.lab6video

package com.example.lab6video

import android.os.SystemClock
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun testVideoPlayPause() {
        // Check if Video is Playing
        onView(withId(R.id.videoButton)).perform(click())
        SystemClock.sleep(1000) // Wait for 1 second for the video to start
        onView(withId(R.id.videoButton)).check(matches(withText("Pause")))

        // Check if Video is Paused
        onView(withId(R.id.videoButton)).perform(click())
        onView(withId(R.id.videoButton)).check(matches(withText("Video")))
    }

    @Test
    fun testRadioPlayStop() {
        // Check if Radio is Playing
        onView(withId(R.id.radioButton)).perform(click())
        SystemClock.sleep(1000) // Wait for 1 second for the radio to start
        onView(withId(R.id.radioButton)).check(matches(withText("Stop")))

        // Check if Radio is Stopped
        onView(withId(R.id.radioButton)).perform(click())
        onView(withId(R.id.radioButton)).check(matches(withText("Play")))
    }

}
