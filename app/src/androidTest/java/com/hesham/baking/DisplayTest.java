package com.hesham.baking;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.hesham.baking.RecyclerViewItemCountAssertion.withItemCount;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DisplayTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private CountingIdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    public static RecyclerMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerMatcher(recyclerViewId);
    }

    @Test
    public void recyclerview_posiiton()
    {
        onView(withRecyclerView(R.id.rv_baking).atPositionOnView(0, R.id.bake_text)).check(matches(withText("Nutella Pie")));
    }

    @Test
    public void retrives_Data()
    {
        onView(withId(R.id.rv_baking)).check(withItemCount(4));
    }

    @Test
    public void steps_display()
    {
        onView(ViewMatchers.withId(R.id.rv_baking)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.rv_steps)).check(matches(isDisplayed()));
    }

    @Test
    public void each_step_display()
    {
        onView(ViewMatchers.withId(R.id.rv_baking)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.rv_steps)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.rv_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.step_description)).check(matches(isDisplayed()));
    }

    @Test
    public void ingredient_display()
    {
        onView(ViewMatchers.withId(R.id.rv_baking)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.rv_steps)).check(matches(isDisplayed()));
        onView((withId(R.id.ingredient_text))).perform(click());
        onView(withId(R.id.rv_ingredients)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}
