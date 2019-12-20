package com.freenow.android_demo.activities;


import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.freenow.android_demo.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

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
