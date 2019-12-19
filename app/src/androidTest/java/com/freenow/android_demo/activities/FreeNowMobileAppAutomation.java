package com.freenow.android_demo.activities;


import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.espresso.util.TreeIterables;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.freenow.android_demo.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FreeNowMobileAppAutomation {

    @Rule
    public ActivityTestRule<AuthenticationActivity> authenticationActivityActivityTestRule = new ActivityTestRule<>(AuthenticationActivity.class);

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    private void enterTextInEditText(int resID,String textToreplace){
        isViewVisible(resID).perform(click())
                .perform(typeText(textToreplace),closeSoftKeyboard());
    }

    private ViewInteraction isViewVisible(int resID){
        return onView(withId(resID)).check(matches(isDisplayed()));
    }

    private void performClicked(int resID){
        isViewVisible(resID).perform(click());
    }

    private void delay(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    @Test
    public void freeNowMobileAppAutomation() {

        enterTextInEditText(R.id.edt_username,"crazydog335");
        enterTextInEditText(R.id.edt_password,"venture");
        delay(1000);
        performClicked(R.id.btn_login);

        delay(2000);
        enterTextInEditText(R.id.textSearch,"sa");
        delay(1200);

        onView(allOf(withText("Samantha Reed"), isDisplayed()))
                .inRoot(RootMatchers.isPlatformPopup()).perform(click());
        delay(1500);

        isViewVisible(R.id.textViewDriverName).check(matches(withText("Samantha Reed")));
        performClicked(R.id.fab);

    }
}
