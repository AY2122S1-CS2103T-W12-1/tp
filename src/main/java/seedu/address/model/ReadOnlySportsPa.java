package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.facility.Facility;
import seedu.address.model.member.Member;

/**
 * Unmodifiable view of a SportsPa
 */
public interface ReadOnlySportsPa {

    /**
     * Returns an unmodifiable view of the member list.
     * This list will not contain any duplicate members.
     */
    ObservableList<Member> getMemberList();

    /**
     * Returns an unmodifiable view of the facility list.
     *
     * @return ObservableList with all facilities.
     */
    ObservableList<Facility> getFacilityList();
}
