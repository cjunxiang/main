package seedu.divelog.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import seedu.divelog.commons.core.LogsCenter;
import seedu.divelog.commons.events.UnitsChangedEvent;
import seedu.divelog.commons.events.ui.DivePanelSelectionChangedEvent;
import seedu.divelog.model.dive.DiveSession;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String FORMAT_DIVE_LOCATION = "Dive @ %s";

    public static final String FORMAT_DIVE_DEPTH = "You dove to %s";

    public static final String FORMAT_START_TIME = "Started at: %s";

    public static final String FORMAT_END_TIME = "Ended at: %s";

    public static final String FORMAT_SAFETY_STOP = "Safety stop at: %s";

    public static final String FORMAT_TIME_NOW = "Date: %s";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label diveLocation;
    @FXML
    private Label diveDepth;
    @FXML
    private Label pgStart;
    @FXML
    private Label pgEnd;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label safetyStop;
    @FXML
    private Label dateTime;

    private DiveSession currentDive;

    public BrowserPanel() {
        super(FXML);
        loadMyTimeNow();
        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);
        registerAsAnEventHandler(this);
    }

    /**
     * Renders the dive specific information
     * @param dive
     */
    private void loadDivePage(DiveSession dive) {
        logger.info("REDRAWING");
        diveLocation.setText(String.format(FORMAT_DIVE_LOCATION, dive.getLocation().getLocationName()));
        diveDepth.setText(String.format(FORMAT_DIVE_DEPTH, dive.getDepthProfile().getFormattedString()));
        pgStart.setText(dive.getPressureGroupAtBeginning().getPressureGroup());
        pgEnd.setText(dive.getPressureGroupAtEnd().getPressureGroup());
        //pgEnd.setTextFill(Color.web("#0076a3"));
        //pgEnd.setForeground(Color.web("#0076a3"));
        //pgEnd.setTextFill(Color.GREEN);
        //pgEnd.setStyle("-fx-background-color: #0076a3;");
        startTime.setText(String.format(FORMAT_START_TIME, dive.getStart().getTimeString()));
        endTime.setText(String.format(FORMAT_END_TIME, dive.getEnd().getTimeString()));
        safetyStop.setText(String.format(FORMAT_SAFETY_STOP, dive.getSafetyStop().getTimeString()));
        dateTime.setText(String.format(FORMAT_TIME_NOW, dive.getDateStart().getOurDateString()));
        checkPressureGrp(dive.getPressureGroupAtBeginning().getPressureGroup());
        currentDive = dive;
        pgStart.getStyleClass().add(checkPressureGrp(dive.getPressureGroupAtBeginning().getPressureGroup()));
        pgEnd.getStyleClass().add(checkPressureGrp(dive.getPressureGroupAtEnd().getPressureGroup()));
    }

    public void freeResources(){

    }
    private void loadMyTimeNow() {
        dateTime.setText(String.format(FORMAT_TIME_NOW, dateTimeSend()));
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(DivePanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadDivePage(event.getNewSelection());
    }
    @Subscribe
    private void handleUnitsChangedEvent(UnitsChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (currentDive != null) {
            Platform.runLater(() -> {
                diveDepth.setText(String.format(FORMAT_DIVE_DEPTH, currentDive.getDepthProfile().getFormattedString()));
            });
        }
    }

    /**
     * Retrieves the current date time as a string
     * @return retrieves date as string
     */
    private String dateTimeSend() {
        Date d = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY hh:mmaa");
        String dateTimeNow = dateFormat.format(d);
        return dateTimeNow;
    }
    /**
     * Receives the pressure group that needs to be parsed
     * sets the attribute on the screen depending on the Pressure group.
     */
    private String checkPressureGrp(String pgGrp) {
        switch(pgGrp) {
        case "A":
            //to return it as #008000 or GREEN or rgb(0,128,0)
            // break;
            return "thisIsGreenA";
        case "B":
            return "thisIsGreen";
        case "C":
            return "thisIsGreen";
        case "D":
            return "thisIsGreen";
        case "E":
            return "thisIsGreen";
        case "F":
            return "thisIsGreen";
        case "G":
            return "thisIsGreen";
        case "H":
            return "thisIsGreen";
        case "I":
            return "thisIsGreen";
        case "J":
            return "thisIsGreen";
        case "L":
            return "thisIsGreen";
        case "M":
            return "thisIsGreen";
        case "N":
            return "thisIsGreen";
        case "O":
            return "thisIsGreen";
        case "P":
            //return #ff6600 or ORANGE or rgb(255, 102, 0)
            return "thisIsOrangeP";
        case "Q":
            return "thisIsOrangeP";
        case "R":
            return "thisIsOrange";
        case "S":
            return "thisIsOrange";
        case "T":
            return "thisIsOrange";
        case "U":
            return "thisIsOrange";
        case "V":
            return "thisIsOrange";
        case "W":
            return "thisIsOrange";
        case "X":
            return "thisIsOrange";
        case "Y":
            return "thisIsOrange";
        case "Z":
            //to return as RED or #ff0000 or (255,0,0)
            return "thisIsRed";
        default:
            //return black
            return "label";
        }
    }
}
