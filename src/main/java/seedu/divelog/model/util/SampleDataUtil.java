package seedu.divelog.model.util;

import seedu.divelog.model.DiveLog;
import seedu.divelog.model.ReadOnlyDiveLog;
import seedu.divelog.model.dive.DepthProfile;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.dive.Location;
import seedu.divelog.model.dive.OurDate;
import seedu.divelog.model.dive.PressureGroup;
import seedu.divelog.model.dive.Time;
import seedu.divelog.model.dive.TimeZone;


/**
 * Contains utility methods for populating {@code DiveLog} with sample data.
 */
public class SampleDataUtil {
    public static DiveSession[] getSampleDives() {
        return new DiveSession[] {
            new DiveSession(new OurDate("04082018"),
                    new Time("0700"),
                    new Time("0945"),
                    new OurDate("04082018"),
                    new Time("1000"),
                    new PressureGroup("A"),
                    new PressureGroup("R"),
                    new Location("Bali"),
                    new DepthProfile(10.0f),
                    new TimeZone("+5"))
        };
    }

    public static ReadOnlyDiveLog getSampleDiveLog() {
        DiveLog sampleDl = new DiveLog();
        for (DiveSession dive : getSampleDives()) {
            sampleDl.addDive(dive);
        }
        return sampleDl;
    }

}
