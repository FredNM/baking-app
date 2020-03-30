package com.frednm.baking_app;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.frednm.baking_app.features.home.HomeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {

    @Rule
    public final ActivityTestRule<HomeActivity> mActivityRule = new ActivityTestRule<>(HomeActivity.class);

    // See https://developer.android.com/reference/android/support/test/espresso/contrib/RecyclerViewActions
    // and https://guides.codepath.com/android/ui-testing-with-espresso
    // and https://www.testchamber.nl/test-automation/testing-recyclerviews-with-espresso/
    @Test
    public void checkClickOnRecipe() {
        // 1- Check RecyclerView of HomeActivity is displayed
        onView(withId(R.id.activity_home_recycler_view)).check(matches(isDisplayed()));
        // 2- Check click on a Recipe item works fine
        onView(withId(R.id.activity_home_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));         // actionOnItem(withText("Nutella Pie"), click()));
        // 3- Check that after the click, the DetailRecipeActivity is displayed
        onView(withId(R.id.frame_layout_recipe)).check(matches(isDisplayed()));
        // 4- Check click on a RecipeStep item works fine
        onView(withId(R.id.fragment_detail_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // 5- Check that on the UI displayed, the text is 'Recipe Introduction'
        onView(withId(R.id.step_description_tv)).check(matches(withText("Recipe Introduction")));
    }

}
