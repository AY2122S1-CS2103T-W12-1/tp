package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.AliasMap;
import seedu.address.model.alias.CommandWord;
import seedu.address.model.alias.Shortcut;
import seedu.address.model.facility.Facility;
import seedu.address.model.member.Member;
import seedu.address.model.sort.SortOrder;


/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Member> PREDICATE_SHOW_ALL_MEMBERS = unused -> true;

    /** {@code Predicate} that always evaluates to true */
    Predicate<Facility> PREDICATE_SHOW_ALL_FACILITIES = unused -> true;

    /** {@code Predicate} that always evaluate to false */
    Predicate<Member> PREDICATE_SHOW_NO_MEMBERS = unused -> false;

    /** {@code Predicate} that always evaluate to false */
    Predicate<Facility> PREDICATE_SHOW_NO_FACILITIES = unused -> false;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getSportsPaFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setSportsPaFilePath(Path sportsPaFilePath);

    /**
     * Returns the user pref's aliases.
     */
    AliasMap getAliases();

    /**
     * Adds the given alias to user prefs.
     */
    void addAlias(Alias alias);

    /**
     * Removes the given alias from user prefs.
     *
     * @param shortcut the shortcut to remove.
     * @return commandWord associated with shortcut, null if alias does not exist.
     */
    CommandWord removeAlias(Shortcut shortcut);

    /**
     * Replaces SportsPA's data with the data in {@code sportsPa}.
     */
    void setSportsPa(ReadOnlySportsPa sportsPa);

    /** Returns the SportsPa */
    ReadOnlySportsPa getSportsPa();

    /**
     * Returns true if a member with the same identity as {@code member} exists in SportsPA.
     */
    boolean hasMember(Member member);

    /**
     * Returns true if a facility with the same parameters as {@code facility} exists in SportsPA.
     */
    boolean hasFacility(Facility facility);

    /**
     * Returns true if all {@code indices} is within the member list.
     */
    boolean isWithinListIndex(List<Index> indices);

    /**
     * Marks attendance of members at specified {@code indices} as present.
     */
    void markMembersAttendance(List<Index> indices);

    /**
     * Marks attendance of specified {@code member} as present.
     */
    void markOneMemberAttendance(Member member);

    /**
     * Unmarks attendance of members at specified {@code indices} as absent.
     */
    void unmarkMembersAttendance(List<Index> indices);

    /**
     * Unmarks attendance of specified {@code member} as absent.
     */
    void unmarkOneMemberAttendance(Member member);

    /**
     * Resets today's attendance for all members.
     */
    void resetTodayAttendance();

    /**
     * Deletes the given member.
     * The member must exist in the address book.
     */
    void deleteMember(Member target);

    /**
     * Removes the given member from all allocations without deleting them.
     * The member must exist in the address book.
     */
    void removeMemberFromAllocations(Member target);

    /**
     * Deletes the given facility.
     * The facility must exist in SportsPA.
     */
    void deleteFacility(Facility target);

    /**
     * Adds the given member.
     * {@code member} must not already exist in SportsPA.
     */
    void addMember(Member member);

    /**
     * Adds the given facility.
     *
     * @param facility Facility to be added.
     */
    void addFacility(Facility facility);

    /**
     * Allocates members into facilities.
     *
     * @param predicate the condition in which specifies the members to allocate.
     * @param dayNumber the day on which to allocate the members.
     * @return number of members left unallocated, -1 if zero members to allocate.
     */
    int split(Predicate<Member> predicate, int dayNumber);

    /**
     * Replaces the given member {@code target} with {@code editedPerson}.
     * {@code target} must exist in SportsPA.
     * The member identity of {@code editedMember} must not be the same as another existing member in SportsPA.
     */
    void setMember(Member target, Member editedMember);

    /**
     * Replaces the given facility {@code target} with {@code editedFacility}.
     * {@code target} must exist in SportsPA.
     * The facility parameters of {@code editedFacility} must not be the same as another existing facility in SportsPA.
     */
    void setFacility(Facility facility, Facility editedFacility);

    /** Returns an unmodifiable view of the filtered member list */
    ObservableList<Member> getFilteredMemberList();

    /**
     * Returns a view of the filtered facility list.
     *
     * @return ObservableList with filtered facilities.
     */
    ObservableList<Facility> getFilteredFacilityList();

    /**
     * Returns a member from SportsPA that has the same name as the given {@code member}.
     *
     * @param member the given member
     * @return a member that has the same name.
     */
    Member getSameMember(Member member);

    /**
     * Checks if the member being imported has the same name as an existing member
     * and same phone number as another existing member at the same time.
     * If the such a situation occurs, the member being imported is deemed invalid.
     *
     * @param toCheck the member being imported
     * @return true if only one or no other members with the same name or phone is found, false otherwise.
     */
    boolean isValidImport(Member toCheck);
    /**
     * Sorts the member list in the specified order.
     */
    void sortMemberList(SortOrder sortOrder);

    /**
     * Clears the contents of the member list.
     */
    void resetMemberList();

    /**
     * Clears the contents of the facility list.
     */
    void resetFacilityList();

    /**
     * Updates the filter of the filtered member list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMemberList(Predicate<Member> predicate);

    /**
     * Updates the filter of the filtered facility list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFacilityList(Predicate<Facility> predicate);

    /**
     * Returns the internal unmodifiable member list.
     */
    ObservableList<Member> getInternalMemberList();

    /**
     * Returns the internal unmodifiable facility list.
     */
    ObservableList<Facility> getInternalFacilityList();
}
