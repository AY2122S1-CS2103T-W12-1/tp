package seedu.address.model.facility;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.facility.exceptions.DuplicateFacilityException;
import seedu.address.model.facility.exceptions.FacilityNotFoundException;
import seedu.address.model.member.Member;

/**
 * Represents a list of facilities.
 */
public class UniqueFacilityList implements Iterable<Facility> {

    private final ObservableList<Facility> facilityList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent facility as the given argument.
     */
    public boolean contains(Facility toCheck) {
        requireNonNull(toCheck);
        return facilityList.stream().anyMatch(toCheck::isSameFacility);
    }

    /**
     * Adds the specified facility to the facilityList.
     *
     * @param facility Facility to be added to list.
     */
    public void add(Facility facility) {
        requireNonNull(facility);
        facilityList.add(facility);
    }

    /**
     * Replaces the facility {@code target} in the list with {@code editedFacility}.
     * {@code target} must exist in the list.
     * The facility parameter of {@code editedFacility} must not be the same as another existing facility in the list.
     *
     * @param target The facility to be replaced.
     * @param editedFacility The facility to replace the target facility.
     */
    public void setFacility(Facility target, Facility editedFacility) {
        requireAllNonNull(target, editedFacility);

        int index = facilityList.indexOf(target);
        if (index == -1) {
            throw new FacilityNotFoundException();
        }

        if (!target.isSameFacility(editedFacility) && contains(editedFacility)) {
            throw new DuplicateFacilityException();
        }

        facilityList.set(index, editedFacility);
    }

    /**
     * Removes the specified facility from the facilityList.
     *
     * @param toRemove Facility to be removed.
     */
    public void remove(Facility toRemove) {
        requireNonNull(toRemove);
        if (!facilityList.remove(toRemove)) {
            throw new FacilityNotFoundException();
        }
    }

    @Override
    public Iterator<Facility> iterator() {
        return facilityList.iterator();
    }

    /**
     * Returns the facilities list.
     *
     * @return ObservableList of containing all facilities.
     */
    public ObservableList<Facility> getObservableList() {
        return facilityList;
    }

    /**
     * Replaces the contents of this list with {@code facilities}.
     * {@code facilities} must not contain duplicate facilities.
     *
     * @param facilities The list of facilities to replace the contents of this list with.
     */
    public void setFacilities(List<Facility> facilities) {
        requireAllNonNull(facilities);
        if (!facilitiesAreUnique(facilities)) {
            throw new DuplicateFacilityException();
        }

        facilityList.setAll(facilities);
    }

    /**
     * Replaces the contents of this list with empty list.
     */
    public void resetFacilities() {
        facilityList.setAll();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueFacilityList // instanceof handles nulls
                && facilityList.equals(((UniqueFacilityList) other).facilityList));
    }

    /**
     * Returns the total capacity of the facilities in the list.
     *
     * @return The total capacity of all facilities in the list.
     */
    public int getTotalCapacity() {
        int count = 0;
        for (Facility facility: facilityList) {
            count += Integer.parseInt(facility.getCapacity().capacity);
        }
        return count;
    }

    /**
     * Allocates members into the different facilities.
     *
     * @param members Members to be allocated.
     * @param dayNumber Day on which to allocate the members.
     */
    public void allocateMembersToFacilitiesOnDay(FilteredList<Member> members, int dayNumber) {
        DayOfWeek day = DayOfWeek.of(dayNumber);
        int index = 0;
        for (Facility facility : facilityList) {
            Facility toEdit = facility;
            toEdit.clearAllocationMapOnDay(day);

            while (!toEdit.isMaxCapacityOnDay(day) && !(index > members.size() - 1)) {
                toEdit.addMemberToFacilityOnDay(members.get(index), day);
                index++;
            }
            this.setFacility(facility, toEdit);
        }
    }

    /**
     * Replaces target Facility with edited Facility. Does not
     * check for duplicates.
     *
     * @param target Facility to be replaced.
     * @param editedFacility Facility replacing old one.
     */
    public void replaceFacility(Facility target, Facility editedFacility) {
        int index = facilityList.indexOf(target);
        if (index == -1) {
            throw new FacilityNotFoundException();
        }

        facilityList.set(index, editedFacility);
    }


    /**
     * Returns true if {@code facilities} contains only unique facilities.
     *
     * @param facilities List of facilities to check for uniqueness.
     * @return true if and only if facilities are unique.
     */
    private boolean facilitiesAreUnique(List<Facility> facilities) {
        for (int i = 0; i < facilities.size() - 1; i++) {
            for (int j = i + 1; j < facilities.size(); j++) {
                if (facilities.get(i).isSameFacility(facilities.get(j))) {
                    return false;
                }
            }
        }
        return true;

    }

    /**
     * Removes a member from all allocations from all facilities.
     *
     * @param key Person to be removed from allocations.
     */
    public void removePersonFromAllocations(Member key) {
        for (Facility facility: facilityList) {
            Facility toEdit = facility;
            toEdit.removeMemberFromFacilityOnAllDays(key);
            this.setFacility(facility, toEdit);
        }
    }
}
