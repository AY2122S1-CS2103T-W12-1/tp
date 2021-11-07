package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.facility.Facility;
import seedu.address.model.facility.UniqueFacilityList;
import seedu.address.model.member.Member;
import seedu.address.model.member.UniqueMemberList;

/**
 * Wraps all data at the SportsPA level
 * Duplicates are not allowed (by .isSameMember comparison)
 */
public class SportsPa implements ReadOnlySportsPa {

    private final UniqueMemberList members;
    private final UniqueFacilityList facilities;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        members = new UniqueMemberList();
        facilities = new UniqueFacilityList();
    }

    public SportsPa() {}

    /**
     * Creates a SportsPa using the Members and Facilities in the {@code toBeCopied}.
     */
    public SportsPa(ReadOnlySportsPa toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the member list with {@code members}.
     * {@code members} must not contain duplicate members.
     */
    public void setMembers(List<Member> members) {
        this.members.setMembers(members);
    }

    /**
     * Replaces the contents of the facility list with {@code facilities}.
     * {@code facilities} must not contain duplicate facilities.
     */
    public void setFacilities(List<Facility> facilities) {
        this.facilities.setFacilities(facilities);
    }

    /**
     * Sorts the member list by name.
     */
    public void sortMemberListByName() {
        this.members.sortMembersByName();
    }

    /**
     * Sorts the member list by tag.
     */
    public void sortMemberListByTags() {
        this.members.sortMembersByTags();
    }
    /**
     * Clears the contents of the member list.
     */
    public void resetMemberList() {
        this.members.resetMembers();
    }

    /**
     * Clears the contents of the facility list.
     */
    public void resetFacilityList() {
        this.facilities.resetFacilities();
    }

    /**
     * Resets today's attendance for all members in list.
     */
    public void resetTodayAttendance() {
        this.members.resetAttendance();
    }

    /**
     * Resets the existing data of this {@code SportsPa} with {@code newData}.
     */
    public void resetData(ReadOnlySportsPa newData) {
        requireNonNull(newData);
        setMembers(newData.getMemberList());
        setFacilities(newData.getFacilityList());
    }

    //// member-level operations

    /**
     * Returns true if a member with the same identity as {@code member} exists in SportsPA.
     */
    public boolean hasMember(Member member) {
        requireNonNull(member);
        return members.contains(member);
    }

    /**
     * Returns true if a facility with the same details as {@code facility} exists in SportsPA.
     */
    public boolean hasFacility(Facility facility) {
        requireNonNull(facility);
        return facilities.contains(facility);
    }

    /**
     * Adds a member to SportsPA.
     * The member must not already exist in the address book.
     */
    public void addMember(Member m) {
        members.add(m);
    }

    /**
     * Adds a facility to the address book.
     *
     * @param f Facility to be added into address book.
     */
    public void addFacility(Facility f) {
        facilities.add(f);
    }

    /**
     * Splits members into different facilities.
     *
     * @param membersFilteredList List of filtered members to be allocated.
     * @return number of members left unallocated, -1 if zero members provided.
     */
    public int split(FilteredList<Member> membersFilteredList, int dayNumber) {
        int memberCount = membersFilteredList.size();
        // facilities have the same capacity everyday so this can be considered the total capacity on the day to split
        int facilityCap = facilities.getTotalCapacity();
        // No members available
        if (memberCount == 0) {
            return -1;
        }
        // Insufficient capacity
        if (memberCount > facilityCap) {
            return memberCount - facilityCap;
        }

        facilities.allocateMembersToFacilitiesOnDay(membersFilteredList, dayNumber);
        return 0;
    }

    /**
     * Replaces the given member {@code target} in the list with {@code editedMember}.
     * {@code target} must exist in SportsPA.
     * The member identity of {@code editedMember} must not be the same as another existing member in SportsPA.
     */
    public void setMember(Member target, Member editedMember) {
        requireNonNull(editedMember);

        members.setMember(target, editedMember);
    }

    /**
     * Replaces the given facility {@code target} in the list with {@code editedFacility}.
     * {@code target} must exist in SportsPA.
     * The facility parameters of {@code editedFacility} must not be the same as another existing facility in SportsPA.
     */
    public void setFacility(Facility target, Facility editedFacility) {
        requireNonNull(editedFacility);

        facilities.setFacility(target, editedFacility);
    }

    /**
     * Removes {@code key} from this {@code SportsPa}.
     * {@code key} must exist in SportsPA.
     */
    public void removePerson(Member key) {
        members.remove(key);
        facilities.removePersonFromAllocations(key);
    }

    /**
     * Removes {@code key} from all allocations.
     * {@code key} must exist in SportsPA.
     */
    public void removeMemberFromAllocations(Member key) {
        facilities.removePersonFromAllocations(key);
    }

    /**
     * Removes {@code key} from SportsPA.
     * {@code key} must exist in SportsPA.
     */
    public void removeFacility(Facility key) {
        facilities.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return members.asUnmodifiableObservableList().size() + " members";
        // TODO: refine later
    }

    @Override
    public ObservableList<Member> getMemberList() {
        return members.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Facility> getFacilityList() {
        return facilities.getObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SportsPa // instanceof handles nulls
                && members.equals(((SportsPa) other).members)
                && facilities.equals(((SportsPa) other).facilities));
    }

    @Override
    public int hashCode() {
        return members.hashCode();
    }
}
