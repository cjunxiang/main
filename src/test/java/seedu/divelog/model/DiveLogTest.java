package seedu.divelog.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.divelog.testutil.TypicalDiveSessions.getTypicalDiveLog;

import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.testutil.DiveSessionBuilder;

public class DiveLogTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final DiveLog diveLog = new DiveLog();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), diveLog.getDiveList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        diveLog.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyDiveLog_replacesData() {
        DiveLog newData = getTypicalDiveLog();
        diveLog.resetData(newData);
        assertEquals(newData, diveLog);
    }

    @Test
    public void hasDiveSession_nullDiveSession_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        diveLog.hasDive(null);
    }


    @Test
    public void getDiveSessionList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        diveLog.getDiveList().remove(0);
    }

    @Test
    public void getMostRecent_test() {
        DiveSession diveSession1 = new DiveSessionBuilder()
                .withStart("1000")
                .withStartDate("01011996")
                .withEnd("1100")
                .withEndDate("01011996").build();
        DiveSession diveSession2 = new DiveSessionBuilder()
                .withStart("1100")
                .withStartDate("01011996")
                .withEnd("1200")
                .withEndDate("01011996").build();
        DiveSession diveSession3 = new DiveSessionBuilder()
                .withStart("1100")
                .withStartDate("01011998")
                .withEnd("1200")
                .withEndDate("01011998").build();
        diveLog.addDive(diveSession1);
        diveLog.addDive(diveSession2);
        diveLog.addDive(diveSession3);
        //Check most recent without dives planned in the future
        assertEquals(diveLog.getMostRecentDive(), diveSession3);
        //TODO: update this test in 100 years :) Hopefully JAVA will be dead by then!
        DiveSession diveSession4 = new DiveSessionBuilder()
                .withStart("1100")
                .withStartDate("01012118")
                .withEnd("1200")
                .withEndDate("01012118").build();
        diveLog.addDive(diveSession4);
        //Check most recent dive. Should still be dive session 3 if system clock is correctly set
        assertEquals(diveLog.getMostRecentDive(), diveSession3);
    }

}
