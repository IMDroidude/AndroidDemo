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
        //onData(withText(R.string.in_email)).perform(click()); //onView works with 100% visible data
        isViewVisible(resID).perform(click())
                .perform(typeText(textToreplace),closeSoftKeyboard());
        //.perform(replaceText(textToreplace),closeSoftKeyboard())  ;
    }

    private ViewInteraction isViewVisible(int resID){
        return onView(withId(resID)).check(matches(isDisplayed()));
    }

    private void performClicked(int resID){
        isViewVisible(resID).perform(click());
    }

    private void delay(long millis){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    @Test
    public void freeNowMobileAppAutomation() {

        /// onView(isRoot()).perform(waitId(R.id.edt_password, TimeUnit.SECONDS.toMillis(15)));
        ///onView(withId(R.id.search_src_text)).perform(typeText("116@wi"), pressImeActionButton());

        enterTextInEditText(R.id.edt_username,"crazydog335");
        enterTextInEditText(R.id.edt_password,"venture");
        delay(1000);
        performClicked(R.id.btn_login);

        delay(2000);

        enterTextInEditText(R.id.textSearch,"sa");

        delay(1200);

        /*onView(withText("Samantha Reed"))
                .inRoot(RootMatchers.isPlatformPopup()).perform(click());*/


        onView(allOf(withText("Samantha Reed"), isDisplayed()))
                .inRoot(RootMatchers.isPlatformPopup()).perform(click());

        /*onData(equalTo("Samantha Reed"))
                .inRoot(RootMatchers.isPlatformPopup()).perform(click());*/

        delay(2000);

        isViewVisible(R.id.textViewDriverName).check(matches(withText("Samantha Reed")));

        ///isViewVisible(R.id.imageViewDriverAvatar);
        ///isViewVisible(R.id.toolbar);

        performClicked(R.id.fab);

    }

    /*private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }*/

    /*public static ViewAction waitId(final int viewId, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for a specific view with id <" + viewId + "> during " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;
                final Matcher<View> viewMatcher = withId(viewId);

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        // found view with required ID
                        if (viewMatcher.matches(child)) {
                            return;
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50);
                }
                while (System.currentTimeMillis() < endTime);

                // timeout happens
                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException())
                        .build();
            }
        };
    }*/
}
