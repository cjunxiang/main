package seedu.divelog.model.dive;

import seedu.divelog.commons.util.CompareUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author arjo
 * This class represents a single dive session
 */
public class DiveSession {
    private final OurDate dateStart;
    private final Time start;
    private final Time safetyStop;
    private final OurDate dateEnd;
    private final Time end;
    private final PressureGroup pressureGroupAtBeginning;
    private final PressureGroup pressureGroupAtEnd;
    private final Location location;
    private final DepthProfile depthProfile;
    private final TimeZone timezone;
    private Date dateTime;
    private long duration;


    public DiveSession(
            OurDate dateStart, Time start, Time safetyStop,
            OurDate dateEnd, Time end,
            PressureGroup pressureGroupAtBeginning,
            PressureGroup pressureGroupAtEnd, Location location,
            DepthProfile depthProfile, TimeZone timezone) {
        this.dateStart = dateStart;
        this.start = start;
        this.safetyStop = safetyStop;
        this.dateEnd = dateEnd;
        this.end = end;
        this.pressureGroupAtBeginning = pressureGroupAtBeginning;
        this.pressureGroupAtEnd = pressureGroupAtEnd;
        this.location = location;
        this.depthProfile = depthProfile;
        this.timezone = timezone;
        String dateTimeString = this.getDateStart().getOurDateString().concat(this.getStart().getTimeString());
        try {
            this.dateTime = new SimpleDateFormat("ddMMyyyyHHmm").parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            this.duration = CompareUtil.checkTimeDifference(this.getStart().getTimeString(), this.getEnd().getTimeString(),
                    this.getDateStart().getOurDateString(), this.getDateEnd().getOurDateString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getDuration() {
        return duration;
    }

    public OurDate getDateStart() {
        return dateStart;
    }

    public OurDate getDateEnd() {
        return dateEnd;
    }

    public Time getStart() {
        return start;
    }

    public Time getEnd() {
        return end;
    }

    public PressureGroup getPressureGroupAtBeginning() {
        return pressureGroupAtBeginning;
    }

    public PressureGroup getPressureGroupAtEnd() {
        return pressureGroupAtEnd;
    }

    public Time getSafetyStop() {
        return safetyStop;
    }

    public DepthProfile getDepthProfile() {
        return depthProfile;
    }

    public Location getLocation() {
        return location;
    }

    public TimeZone getTimeZone() {
        return timezone;
    }

    public Date getDateTime() {
        return dateTime;
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof DiveSession)) {
            return false;
        }
        DiveSession other = (DiveSession) obj;
        return other.getLocation().equals(getLocation())
                && other.getDateStart().equals(getDateStart())
                && other.getDateEnd().equals(getDateEnd())
                && other.getStart().equals(getStart())
                && other.getSafetyStop().equals(getSafetyStop())
                && other.getPressureGroupAtBeginning().equals(getPressureGroupAtBeginning())
                && other.getPressureGroupAtEnd().equals(getPressureGroupAtEnd())
                && other.getEnd().equals(getEnd())
                && other.getDepthProfile().equals(getDepthProfile())
                && other.getTimeZone().equals(getTimeZone());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Dive Location: " + getLocation().getLocationName() + "\n");
        stringBuilder.append("\tStart: " + getDateStart().getOurDateString() + " " + getStart().getTimeString() + "\n");
        stringBuilder.append("\tEnd: " + getDateEnd().getOurDateString() + " " + getEnd().getTimeString() + "\n");
        stringBuilder.append("\tPressureGroup: " + getPressureGroupAtBeginning().getPressureGroup() + "->"
                + getPressureGroupAtEnd().getPressureGroup() + "\n");
        stringBuilder.append("\tDepth: " + getDepthProfile().getDepth() + "\n");
        stringBuilder.append("\tTime Zone:" + getTimeZone().getTimeZoneString());
        return stringBuilder.toString();
    }
}
