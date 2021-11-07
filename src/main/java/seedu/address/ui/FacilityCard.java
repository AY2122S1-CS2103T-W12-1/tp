package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.facility.Facility;

/**
 * An Ui component that displays information of a Facility
 */
public class FacilityCard extends UiPart<Region> {

    private static final String FXML = "FacilityCard.fxml";

    public final Facility facility;

    @FXML
    private HBox cardPane;

    @FXML
    private Label name;

    @FXML
    private Label loc;

    @FXML
    private Label time;

    @FXML
    private Label capacity;

    @FXML
    private Label listIndex;

    @FXML
    private Label memberAllocated;

    /**
     * Creates a FacilityCard with the specified facility and index.
     *
     * @param facility Facility to be listed.
     * @param index Index of facility in list.
     */
    public FacilityCard(Facility facility, int index) {
        super(FXML);
        this.facility = facility;
        listIndex.setText(index + ".");
        name.setText(facility.getName().toString());
        loc.setText(facility.getLocation().toString());
        time.setText("Start time: " + facility.getTime().toString());
        capacity.setText("Max capacity: " + facility.getCapacity().toString());
        memberAllocated.setText(facility.getAllocationMap().toString());
    }

}
