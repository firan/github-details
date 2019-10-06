package com.example.applausegithubapp.matchers

import android.view.View
import androidx.test.espresso.core.internal.deps.guava.base.Predicate
import androidx.test.espresso.core.internal.deps.guava.collect.Iterables
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import androidx.test.espresso.util.TreeIterables

class CountMatcher(
    private val expectedCount: Int,
    private val viewMatcher: Matcher<View>
) : TypeSafeMatcher<View>() {

    var actualCount = -1

    companion object {
        fun withViewCount(viewMatcher: Matcher<View>, expectedCount: Int): Matcher<View> {
            return CountMatcher(expectedCount, viewMatcher)
        }

        private fun withMatcherPredicate(matcher: Matcher<View>): Predicate<View> {
            return Predicate { view -> matcher.matches(view) }
        }
    }

    override fun describeTo(description: Description?) {
        if (actualCount >= 0) {
            description?.appendText("With expected number of items: $expectedCount")
            description?.appendText("\n With matcher: ")
            viewMatcher.describeTo(description)
            description?.appendText("\n But got: $actualCount")
        }
    }

    @Suppress("DEPRECATED_IDENTITY_EQUALS")
    override fun matchesSafely(item: View?): Boolean {
        actualCount = 0
        val iterable = TreeIterables.breadthFirstViewTraversal(item)
        actualCount = Iterables.filter(iterable, withMatcherPredicate(viewMatcher)).count()
        return actualCount === expectedCount
    }
}
