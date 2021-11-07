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
import seedu.address.model.member.Member;
import seedu.address.model.sort.SortOrder;


/**
 * Represents the in-memory model of SportsPA's data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final SportsPa sportsPa;
    private final UserPrefs userPrefs;
    private final FilteredList<Member> filteredMembers;
    private final FilteredList<Facility> filteredFacilities;

    /**
     * Initializes a ModelManager with the given sportsPa and userPrefs.
     */
    public ModelManager(ReadOnlySportsPa sportsPa, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(sportsPa, userPrefs);

        logger.fine("Initializing with SportsPA: " + sportsPa + " and user prefs " + userPrefs);

        this.sportsPa = new SportsPa(sportsPa);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredMembers = new FilteredList<>(this.sportsPa.getMemberList());
        filteredFacilities = new FilteredList<>(this.sportsPa.getFacilityList());
    }

    public ModelManager() {
        this(new SportsPa(), new UserPrefs());
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
    public Path getSportsPaFilePath() {
        return userPrefs.getSportsPaFilePath();
    }

    @Override
    public void setSportsPaFilePath(Path sportsPaFilePath) {
        requireNonNull(sportsPaFilePath);
        userPrefs.setSportsPaFilePath(sportsPaFilePath);
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

    //=========== SportsPa ================================================================================

    @Override
    public void setSportsPa(ReadOnlySportsPa sportsPa) {
        this.sportsPa.resetData(sportsPa);
    }

    @Override
    public ReadOnlySportsPa getSportsPa() {
        return sportsPa;
    }

    @Override
    public boolean hasMember(Member member) {
        requireNonNull(member);
        return sportsPa.hasMember(member);
    }

    @Override
    public boolean hasFacility(Facility facility) {
        requireNonNull(facility);
        return sportsPa.hasFacility(facility);
    }

    @Override
    public boolean isWithinListIndex(List<Index> indices) {
        for (Index i : indices) {
            if (i.getZeroBased() >= getFilteredMemberList().size()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void markMembersAttendance(List<Index> indices) {
        for (Index i : indices) {
            Member member = filteredMembers.get(i.getZeroBased());
            markOneMemberAttendance(member);
        }
    }

    @Override
    public void markOneMemberAttendance(Member member) {
        Member toEdit = member;
        toEdit.setPresent();
        setMember(member, toEdit);
    }

    @Override
    public void unmarkMembersAttendance(List<Index> indices) {
        for (Index i : indices) {
            Member member = filteredMembers.get(i.getZeroBased());
            unmarkOneMemberAttendance(member);
        }
    }

    @Override
    public void unmarkOneMemberAttendance(Member member) {
        Member toEdit = member;
        toEdit.setNotPresent();
        setMember(member, toEdit);
    }

    @Override
    public void resetTodayAttendance() {
        sportsPa.resetTodayAttendance();
    }

    @Override
    public void deleteMember(Member target) {
        sportsPa.removePerson(target);
    }

    @Override
    public void removeMemberFromAllocations(Member target) {
        sportsPa.removeMemberFromAllocations(target);
    }

    @Override
    public void deleteFacility(Facility target) {
        sportsPa.removeFacility(target);
    }

    @Override
    public void addMember(Member member) {
        sportsPa.addMember(member);
        updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
    }

    @Override
    public void addFacility(Facility facility) {
        sportsPa.addFacility(facility);
    }

    @Override
    public int split(Predicate<Member> predicate, int dayNumber) {
        FilteredList<Member> toAllocate = new FilteredList<>(sportsPa.getMemberList());
        toAllocate.setPredicate(predicate);
        return sportsPa.split(toAllocate, dayNumber);
    }

    @Override
    public void setMember(Member target, Member editedMember) {
        requireAllNonNull(target, editedMember);

        sportsPa.setMember(target, editedMember);
    }

    @Override
    public void setFacility(Facility target, Facility editedFacility) {
        requireAllNonNull(target, editedFacility);
        sportsPa.setFacility(target, editedFacility);
    }

    @Override
    public Member getSameMember(Member toFind) {
        requireNonNull(toFind);
        return sportsPa.getMemberList()
                .stream()
                .filter(member -> member.isSameMember(toFind))
                .findAny()
                .orElse(null);
    }

    @Override
    public boolean isValidImport(Member toCheck) {
        requireNonNull(toCheck);
        long count = sportsPa.getMemberList()
                .stream()
                .filter(member -> member.isSameMember(toCheck))
                .count();
        return count <= 1;
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
        sportsPa.sortMemberListByName();
    }


    private void sortMemberListByTags() {
        sportsPa.sortMemberListByTags();
    }

    @Override
    public void resetMemberList() {
        sportsPa.resetMemberList();
    }

    @Override
    public void resetFacilityList() {
        sportsPa.resetFacilityList();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Member> getFilteredMemberList() {
        return filteredMembers;
    }

    @Override
    public void updateFilteredMemberList(Predicate<Member> predicate) {
        requireNonNull(predicate);
        filteredMembers.setPredicate(predicate);
    }

    @Override
    public ObservableList<Member> getInternalMemberList() {
        return sportsPa.getMemberList();
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
        return sportsPa.equals(other.sportsPa)
                && userPrefs.equals(other.userPrefs)
                && filteredMembers.equals(other.filteredMembers)
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

    @Override
    public ObservableList<Facility> getInternalFacilityList() {
        return sportsPa.getFacilityList();
    }
}
