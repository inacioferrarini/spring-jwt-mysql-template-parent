package com.inacioferrarini.test.hamcrest.matchers;

import org.hamcrest.Matcher;

public class CustomMatchers {

    public static Matcher<String> IsDaysFromNowMatcher(Long days) {
        return new IsDaysFromNowMatcher(days);
    }

}
