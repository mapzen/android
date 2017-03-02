package com.mapzen.android.sdk.sample;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

  @Rule public ActivityTestRule<MainActivity> activityRule =
      new ActivityTestRule(MainActivity.class);

  @Test public void useAppContext() throws Exception {
    Context appContext = InstrumentationRegistry.getTargetContext();
    assertEquals("com.mapzen.android.sdk.sample", appContext.getPackageName());
  }

  @Test public void shouldDisplayDemoList() {
    onView(withText("Basic Map")).check(matches(isDisplayed()));
  }
}
