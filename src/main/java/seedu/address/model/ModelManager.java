package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.AliasMap;
import seedu.address.model.alias.CommandWord;
import seedu.address.model.alias.Shortcut;
import seedu.address.model.facility.Facility;
import seedu.address.model.person.Person;
import seedu.address.model.sort.SortOrder;


/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Facility> filteredFacilities;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredFacilities = new FilteredList<>(this.addressBook.getFacilityList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public AliasMap getAliases() {
        return userPrefs.getAliases();
    }

    @Override
    public void addAlias(Alias alias) {
        requireNonNull(alias);
        userPrefs.addAlias(alias);
    }

    @Override
    public CommandWord removeAlias(Shortcut shortcut) {
        requireNonNull(shortcut);
        return userPrefs.removeAlias(shortcut);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public boolean hasFacility(Facility facility) {
        requireNonNull(facility);
        return addressBook.hasFacility(facility);
    }

    @Override
    public boolean isWithinListIndex(List<Index> indices) {
        for (Index i : indices) {
            if (i.getZeroBased() >= getFilteredPersonList().size()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void markMembersAttendance(List<Index> indices) {
        for (Index i : indices) {
            Person person = filteredPersons.get(i.getZeroBased());
            markOneMemberAttendance(person);
        }
    }

    @Override
    public void markOneMemberAttendance(Person person) {
        Person toEdit = person;
        toEdit.setPresent();
        setPerson(person, toEdit);
    }

    @Override
    public void unmarkMembersAttendance(List<Index> indices) {
        for (Index i : indices) {
            Person person = filteredPersons.get(i.getZeroBased());
            unmarkOneMemberAttendance(person);
        }
    }

    @Override
    public void unmarkOneMemberAttendance(Person person) {
        Person toEdit = person;
        toEdit.setNotPresent();
        setPerson(person, toEdit);
    }

    @Override
    public void resetTodayAttendance() {
        addressBook.resetTodayAttendance();
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void deleteFacility(Facility target) {
        addressBook.removeFacility(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addFacility(Facility facility) {
        addressBook.addFacility(facility);
    }

    @Override
    public int split(Predicate<Person> predicate) {
        FilteredList<Person> toAllocate = new FilteredList<Person>(addressBook.getPersonList());
        toAllocate.setPredicate(predicate);
        return addressBook.split(toAllocate);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public void setFacility(Facility target, Facility editedFacility) {
        requireAllNonNull(target, editedFacility);
        addressBook.setFacility(target, editedFacility);
    }

    @Override
    public void sortMemberList(SortOrder sortOrder) {
        requireNonNull(sortOrder);
        String order = sortOrder.toString();
        switch (order) {
        case "name":
            sortMemberListByName();
            break;
        case "tag":
            sortMemberListByTags();
            break;
        default:
        }
    }

    private void sortMemberListByName() {
        addressBook.sortMemberListByName();
    }


    private void sortMemberListByTags() {
        addressBook.sortMemberListByTags();
    }

    @Override
    public void resetMemberList() {
        addressBook.resetMemberList();
    }

    @Override
    public void resetFacilityList() {
        addressBook.resetFacilityList();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && filteredFacilities.equals(other.filteredFacilities);
    }

    //=========== Filtered Facility List Accessors =============================================================

    @Override
    public ObservableList<Facility> getFilteredFacilityList() {
        return filteredFacilities;
    }

    @Override
    public void updateFilteredFacilityList(Predicate<Facility> predicate) {
        requireNonNull(predicate);
        filteredFacilities.setPredicate(predicate);
    }

}
