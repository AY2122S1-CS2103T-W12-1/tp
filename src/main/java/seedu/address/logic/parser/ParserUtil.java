package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.alias.CommandWord;
import seedu.address.model.alias.Shortcut;
import seedu.address.model.facility.Capacity;
import seedu.address.model.facility.FacilityName;
import seedu.address.model.facility.Location;
import seedu.address.model.facility.Time;
import seedu.address.model.member.Address;
import seedu.address.model.member.Availability;
import seedu.address.model.member.Name;
import seedu.address.model.member.Phone;
import seedu.address.model.member.TodayAttendance;
import seedu.address.model.member.TotalAttendance;
import seedu.address.model.sort.SortOrder;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer,"
            + "and it should not be blank.";
    private static final String MESSAGE_INVALID_TODAY_ATTENDANCE = "Today Attendance should be 'true' or false',"
            + "and it should not be blank.";
    private static final String MESSAGE_INVALID_TOTAL_ATTENDANCE = "Total Attendance should be a "
            + "non-negative unsigned integer, and it should not be blank.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String todayAttendance} into a {@code TodayAttendance}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code todayAttendance} is invalid.
     */
    public static TodayAttendance parseTodayAttendance(String todayAttendance) throws ParseException {
        String trimmedTodayAttendance = todayAttendance.trim();
        if (!StringUtil.isValidBooleanValue(trimmedTodayAttendance)) {
            throw new ParseException(MESSAGE_INVALID_TODAY_ATTENDANCE);
        }
        return new TodayAttendance(Boolean.parseBoolean(trimmedTodayAttendance));
    }

    /**
     * Parses a {@code String totalAttendance} into a {@code TotalAttendance}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code totalAttendance} is invalid.
     */
    public static TotalAttendance parseTotalAttendance(String totalAttendance) throws ParseException {
        String trimmedTotalAttendance = totalAttendance.trim();
        if (!StringUtil.isNonNegativeUnsignedInteger(trimmedTotalAttendance)) {
            throw new ParseException(MESSAGE_INVALID_TOTAL_ATTENDANCE);
        }
        return new TotalAttendance(Integer.parseInt(trimmedTotalAttendance));
    }
    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a string into FacilityName. Leading or trailing
     * whitespaces are trimmed.
     *
     * @param name String to be parsed.
     * @return FacilityName object with specified name.
     * @throws ParseException if given facility name is invalid.
     */
    public static FacilityName parseFacilityName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!FacilityName.isValidFacilityName(trimmedName)) {
            throw new ParseException(FacilityName.MESSAGE_CONSTRAINTS);
        }
        return new FacilityName(trimmedName);
    }

    /**
     * Parses a string into Location. Leading or trailing
     * whitespaces are trimmed.
     *
     * @param location String to be parsed.
     * @return Location object with specified value.
     * @throws ParseException if given location is invalid.
     */
    public static Location parseLocation(String location) throws ParseException {
        requireNonNull(location);
        String trimmedLocation = location.trim();
        if (!Location.isValidLocation(trimmedLocation)) {
            throw new ParseException(Location.MESSAGE_CONSTRAINTS);
        }
        return new Location(trimmedLocation);
    }

    /**
     * Parses a string into Time. Leading or trailing
     * whitespaces are trimmed.
     *
     * @param time String to be parsed.
     * @return Time object with specified value.
     * @throws ParseException if the given time is invalid.
     */
    public static Time parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!Time.isValidTime(trimmedTime)) {
            throw new ParseException(Time.MESSAGE_CONSTRAINTS);
        }
        return new Time(trimmedTime);
    }

    /**
     * Parses a string into Capacity. Leading or trailing
     * whitespaces are trimmed.
     *
     * @param capacity String to be parsed.
     * @return Capacity object with specified value.
     * @throws ParseException if given capacity is invalid.
     */
    public static Capacity parseCapacity(String capacity) throws ParseException {
        requireNonNull(capacity);
        String trimmedCapacity = capacity.trim();
        if (!Capacity.isValidCapacity(trimmedCapacity)) {
            throw new ParseException(Capacity.MESSAGE_CONSTRAINTS);
        }
        return new Capacity(trimmedCapacity);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String shortcut} into Shortcut.
     */
    public static Shortcut parseShortcut(String shortcut) throws ParseException {
        requireNonNull(shortcut);
        String trimmedShortcut = shortcut.trim();
        if (!Shortcut.isValidShortcut(trimmedShortcut)) {
            throw new ParseException(Shortcut.MESSAGE_CONSTRAINTS);
        }
        return new Shortcut(trimmedShortcut);
    }

    /**
     * Parses a {@code String commandWord} into commandWord.
     */
    public static CommandWord parseCommandWord(String commandWord) throws ParseException {
        requireNonNull(commandWord);
        String trimmedCommandWord = commandWord.trim();
        if (!CommandWord.isValidCommandWord(trimmedCommandWord)) {
            throw new ParseException(CommandWord.MESSAGE_CONSTRAINTS);
        }
        return new CommandWord(trimmedCommandWord);
    }

    /**
     * Parses an {@code String availabilityString} into an {@code Availability}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code availability string} is invalid.
     */
    public static Availability parseAvailability(String availabilityString) throws ParseException {
        requireNonNull(availabilityString);
        String trimmedAvailabilityString = availabilityString.trim();
        List<String> availabilityDaysWithNoDuplicates =
                Arrays.stream(trimmedAvailabilityString.split(" "))
                .distinct().collect(Collectors.toList());

        if (!Availability.isValidAvailability(availabilityDaysWithNoDuplicates)) {
            throw new ParseException(Availability.MESSAGE_CONSTRAINTS);
        }

        List<DayOfWeek> availability;
        if (availabilityDaysWithNoDuplicates.get(0).isEmpty()) { // valid but empty
            availability = new ArrayList<>();
        } else {
            availability = Arrays.stream(trimmedAvailabilityString.split(" "))
                    .distinct().map(dayNumber -> DayOfWeek.of(Integer.parseInt(dayNumber)))
                    .collect(Collectors.toList());
        }
        Collections.sort(availability);
        return new Availability(availability);
    }

    /**
     * Parses a {@code String dayString} into DayOfWeek.
     */
    public static DayOfWeek parseDay(String dayString) throws ParseException {
        requireNonNull(dayString);
        String validationRegex = "[1-7]";
        if (!dayString.matches(validationRegex)) {
            throw new ParseException(CommandWord.MESSAGE_CONSTRAINTS);
        }
        int dayNumber = Integer.parseInt(dayString);
        return DayOfWeek.of(dayNumber);
    }

    /**
     * Parses a {@code String sortOrder} into sortOrder.
     */
    public static SortOrder parseSortOrder(String sortOrder) throws ParseException {
        requireNonNull(sortOrder);
        String trimmedSortOrder = sortOrder.trim().toLowerCase();
        if (!SortOrder.isValidSortOrder(trimmedSortOrder)) {
            throw new ParseException(SortOrder.MESSAGE_CONSTRAINTS);
        }
        return new SortOrder(trimmedSortOrder);
    }
}
