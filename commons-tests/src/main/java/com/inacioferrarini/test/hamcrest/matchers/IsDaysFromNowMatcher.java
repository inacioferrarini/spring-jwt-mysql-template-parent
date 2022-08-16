package com.inacioferrarini.test.hamcrest.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Optional;

public class IsDaysFromNowMatcher extends TypeSafeMatcher<String> {

    final private Long days;
    private Long parsedDuration;

    public IsDaysFromNowMatcher(Long days) {
        this.days = days;
    }

    @Override
    protected boolean matchesSafely(String value) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(value);
            java.sql.Timestamp parsedTimestamp = new java.sql.Timestamp(date.getTime());

            Timestamp now = new Timestamp(new Date().getTime());
            Long duration = Duration.between(now.toInstant(), parsedTimestamp.toInstant()).toDays();
            parsedDuration = duration;
            return duration.equals(days);
        } catch (ParseException exception) {
            return false;
        }
    }

    @Override
    public void describeTo(Description description) {
        Optional<Long> parsedDifference = Optional.of(this.parsedDuration);
        parsedDifference.ifPresentOrElse(
                (value) -> description.appendText("" + value + " days"),
                () -> description.appendText("" + this.days + " days")
        );
    }

}
