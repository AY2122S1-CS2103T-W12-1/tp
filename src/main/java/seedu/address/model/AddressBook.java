package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.facility.Facility;
import seedu.address.model.facility.UniqueFacilityList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueFacilityList facilities;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        facilities = new UniqueFacilityList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons and Facilities in the {@code toBeCopied}.
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
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
        this.persons.sortMembersByName();
    }

    /**
     * Sorts the member list by tag.
     */
    public void sortMemberListByTags() {
        this.persons.sortMembersByTags();
    }
    /**
     * Clears the contents of the member list.
     */
    public void resetMemberList() {
        this.persons.resetMembers();
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
        this.persons.resetAttendance();
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setPersons(newData.getPersonList());
        setFacilities(newData.getFacilityList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Returns true if a facility with the same details as {@code facility} exists in the address book.
     */
    public boolean hasFacility(Facility facility) {
        requireNonNull(facility);
        return facilities.contains(facility);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
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
    public int split(FilteredList<Person> membersFilteredList) {
        int memberCount = membersFilteredList.size();
        int facilityCap = facilities.getTotalCapacity();
        // No members available
        if (memberCount == 0) {
            return -1;
        }
        // Insufficient capacity
        if (memberCount > facilityCap) {
            return memberCount - facilityCap;
        }

        facilities.allocateMembersToFacilities(membersFilteredList);
        return 0;
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
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
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
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
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Facility> getFacilityList() {
        return facilities.getObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons)
                && facilities.equals(((AddressBook) other).facilities));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
