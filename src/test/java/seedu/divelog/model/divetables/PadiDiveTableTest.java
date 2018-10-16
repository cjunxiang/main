package seedu.divelog.model.divetables;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;

import seedu.divelog.model.dive.DepthProfile;
import seedu.divelog.model.dive.PressureGroup;

//@@author arjo129
public class PadiDiveTableTest {

    @Test
    public void getSurfaceTable_canRead() throws JSONException {
        PadiDiveTable padiDiveTable = PadiDiveTable.getInstance();
        JSONArray arr = padiDiveTable.getSurfaceTable(new PressureGroup("A"), new PressureGroup("A"));
        assertEquals(arr.get(0).toString(),"00:00");
        assertEquals(arr.get(1).toString(),"03:00");
    }

    @Test
    public void depthToPressuregroup_canRead() {
        PadiDiveTable padiDiveTable = PadiDiveTable.getInstance();
        PressureGroup pg = padiDiveTable.depthToPg(new DepthProfile(10), 122);
        assertEquals(pg.getPressureGroup(),"T");
    }

    @Test
    public void depthToTimes_canRead() throws JSONException {
        PadiDiveTable padiDiveTable = PadiDiveTable.getInstance();
        JSONArray arr = padiDiveTable.depthToTimes(new DepthProfile(10), new PressureGroup("A"));
        assertEquals(arr.getInt(0), 10);
        assertEquals(arr.getInt(1),209);
    }
}
