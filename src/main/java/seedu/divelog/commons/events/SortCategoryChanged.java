package seedu.divelog.commons.events;

import seedu.divelog.commons.enums.SortCategory;

//@@author Cjunx
/*
* Event for when Sorting category changes
 */
public class SortCategoryChanged extends BaseEvent {
    private final SortCategory sortCategory;

    public SortCategoryChanged(SortCategory sortCategory){
        this.sortCategory = sortCategory;
    }

    @Override
    public String toString() {
        return "SortCategoryChangedEvent fired. Units set to: " + sortCategory;
    }
}
