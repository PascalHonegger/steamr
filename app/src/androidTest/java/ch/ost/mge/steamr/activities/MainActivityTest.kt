package ch.ost.mge.steamr.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import ch.ost.mge.steamr.R
import kotlinx.serialization.ExperimentalSerializationApi
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val steamId = "12345"
private const val PACKAGE_NAME = "ch.ost.mge.steamr"

@ExperimentalXmlUtilApi
@ExperimentalSerializationApi
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun givenSteamIdOpensProfileActivity() {

        // Enter a steam id
        onView(withId(R.id.editTextSteamId))
            .perform(typeText(steamId), closeSoftKeyboard())

        // Click on "view profile" opens ProfileActivity
        // with steam id through an explicit intent.
        onView(withId(R.id.viewProfileButton)).perform(click())

        // Verifies that the ProfileActivity received an intent
        // with the correct package name and message.
        intended(
            allOf(
                hasComponent(hasShortClassName(".activities.ProfileActivity")),
                toPackage(PACKAGE_NAME),
                hasExtra("SteamProfileId", steamId)
            )
        )
    }

    @Test
    fun givenNoSteamIdButtonDisabled() {
        onView(withId(R.id.viewProfileButton))
            .check(matches(not(isEnabled())))
    }

    @Test
    fun givenBlankSteamIdButtonDisabled() {
        onView(withId(R.id.editTextSteamId))
            .perform(typeText("dummy"), clearText(), typeText("   "), closeSoftKeyboard())

        onView(withId(R.id.viewProfileButton))
            .check(matches(not(isEnabled())))
    }
}
