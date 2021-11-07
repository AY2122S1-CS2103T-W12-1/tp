package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.alias.CommandWord;
import seedu.address.model.alias.Shortcut;
import seedu.address.model.facility.Capacity;
import seedu.address.model.facility.FacilityName;
import seedu.address.model.facility.Location;
import seedu.address.model.facility.Time;
import seedu.address.model.member.Address;
import seedu.address.model.member.Name;
import seedu.address.model.member.Phone;
import seedu.address.model.member.TodayAttendance;
import seedu.address.model.member.TotalAttendance;
import seedu.address.model.tag.Tag;


public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_TODAY_ATTENDANCE = "f";
    private static final String INVALID_TOTAL_ATTENDANCE = "-10";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_TODAY_ATTENDANCE = "false";
    private static final String VALID_TOTAL_ATTENDANCE = "3";

    private static final String INVALID_FACILITY_NAME = "Cour+";
    private static final String INVALID_FACILITY_LOCATION = "University H@ll";
    private static final String INVALID_FACILITY_TIME = "555";
    private static final String INVALID_FACILITY_CAPACITY = "Z";

    private static final String VALID_FACILITY_NAME = "Court";
    private static final String VALID_FACILITY_LOCATION = "University Hall";
    private static final String VALID_FACILITY_TIME = "1130";
    private static final String VALID_FACILITY_CAPACITY = "5";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseFacilityName_null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseFacilityName((String) null));
    }

    @Test
    public void parseFacilityName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseFacilityName(INVALID_FACILITY_NAME));
    }

    @Test
    public void parseFacilityName_valueWithoutWhiteSpace_returnsFacilityName() throws Exception {
        FacilityName name = new FacilityName(VALID_FACILITY_NAME);
        assertEquals(name, ParserUtil.parseFacilityName(VALID_FACILITY_NAME));
    }

    @Test
    public void parseFacilityName_valueWithWhiteSpace_returnsTrimmedName() throws Exception {
        FacilityName name = new FacilityName(VALID_FACILITY_NAME);
        assertEquals(name, ParserUtil.parseFacilityName(WHITESPACE + VALID_FACILITY_NAME + WHITESPACE));
    }

    @Test
    public void parseLocation_null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLocation((String) null));
    }

    @Test
    public void parseLocation_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLocation(INVALID_FACILITY_LOCATION));
    }

    @Test
    public void parseLocation_valueWithoutWhiteSpace_returnsLocation() throws Exception {
        Location location = new Location(VALID_FACILITY_LOCATION);
        assertEquals(location, ParserUtil.parseLocation(VALID_FACILITY_LOCATION));
    }

    @Test
    public void parseLocation_valueWithWhiteSpace_returnsTrimmedLocation() throws Exception {
        Location location = new Location(VALID_FACILITY_LOCATION);
        assertEquals(location, ParserUtil.parseLocation(WHITESPACE + VALID_FACILITY_LOCATION + WHITESPACE));
    }

    @Test
    public void parseTime_null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTime((String) null));
    }

    @Test
    public void parseTime_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTime(INVALID_FACILITY_TIME));
    }

    @Test
    public void parseTime_valueWithoutWhiteSpace_returnsTime() throws Exception {
        Time time = new Time(VALID_FACILITY_TIME);
        assertEquals(time, ParserUtil.parseTime(VALID_FACILITY_TIME));
    }

    @Test
    public void parseTime_valueWithWhiteSpace_returnsTrimmedTime() throws Exception {
        Time time = new Time(VALID_FACILITY_TIME);
        assertEquals(time, ParserUtil.parseTime(WHITESPACE + VALID_FACILITY_TIME + WHITESPACE));
    }

    @Test
    public void parseCapacity_null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCapacity((String) null));
    }

    @Test
    public void parseCapacity_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCapacity(INVALID_FACILITY_CAPACITY));
    }

    @Test
    public void parseCapacity_valueWithoutWhiteSpace_returnsCapacity() throws Exception {
        Capacity capacity = new Capacity(VALID_FACILITY_CAPACITY);
        assertEquals(capacity, ParserUtil.parseCapacity(VALID_FACILITY_CAPACITY));
    }

    @Test
    public void parseCapacity_valueWithWhiteSpace_returnsTrimmedCapacity() throws Exception {
        Capacity capacity = new Capacity(VALID_FACILITY_CAPACITY);
        assertEquals(capacity, ParserUtil.parseCapacity(WHITESPACE + VALID_FACILITY_CAPACITY + WHITESPACE));
    }



    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseTodayAttendance_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTodayAttendance((String) null));
    }

    @Test
    public void parseTodayAttendance_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTodayAttendance(INVALID_TODAY_ATTENDANCE));
    }

    @Test
    public void parseTodayAttendance_validValueWithoutWhitespace_returnsTodayAttendance() throws Exception {
        TodayAttendance expectedTodayAttendance = new TodayAttendance(false);
        assertEquals(expectedTodayAttendance, ParserUtil.parseTodayAttendance(VALID_TODAY_ATTENDANCE));
    }

    @Test
    public void parseTodayAttendance_validValueWithWhitespace_returnsTodayAttendance() throws Exception {
        String todayAttendanceWithWhiteSpace = WHITESPACE + VALID_TODAY_ATTENDANCE + WHITESPACE;
        TodayAttendance expectedTodayAttendance = new TodayAttendance(false);
        assertEquals(expectedTodayAttendance, ParserUtil.parseTodayAttendance(todayAttendanceWithWhiteSpace));
    }

    @Test
    public void parseTotalAttendance_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTotalAttendance((String) null));
    }

    @Test
    public void parseTotalAttendance_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTotalAttendance(INVALID_TOTAL_ATTENDANCE));
    }

    @Test
    public void parseTotalAttendance_validValueWithoutWhitespace_returnsTotalAttendance() throws Exception {
        TotalAttendance expectedTotalAttendance = new TotalAttendance(3);
        assertEquals(expectedTotalAttendance, ParserUtil.parseTotalAttendance(VALID_TOTAL_ATTENDANCE));
    }

    @Test
    public void parseTotalAttendance_validValueWithWhitespace_returnsTotalAttendance() throws Exception {
        String totalAttendanceWithWhiteSpace = WHITESPACE + VALID_TOTAL_ATTENDANCE + WHITESPACE;
        TotalAttendance expectedTotalAttendance = new TotalAttendance(3);
        assertEquals(expectedTotalAttendance, ParserUtil.parseTotalAttendance(totalAttendanceWithWhiteSpace));
    }

    @Test
    public void parseShortcut_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseShortcut((String) null));
    }

    @Test
    public void parseShortcut_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseShortcut("   "));
    }

    @Test
    public void parseShortcut_validValueWithoutWhitespace_returnsShortcut() throws Exception {
        Shortcut expectedShortcut = new Shortcut("lf");
        assertEquals(expectedShortcut, ParserUtil.parseShortcut("lf"));
    }

    @Test
    public void parseShortcut_validValueWithWhitespace_returnsTrimmedShortcut() throws Exception {
        String shortcutWithWhitespace = WHITESPACE + "lf" + WHITESPACE;
        Shortcut expectedShortcut = new Shortcut("lf");
        assertEquals(expectedShortcut, ParserUtil.parseShortcut(shortcutWithWhitespace));
    }

    @Test
    public void parseCommandWord_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCommandWord((String) null));
    }

    @Test
    public void parseCommandWord_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCommandWord("   "));
        assertThrows(ParseException.class, () -> ParserUtil.parseCommandWord("bing"));
    }

    @Test
    public void parseCommandWord_validValueWithoutWhitespace_returnsCommandWord() throws Exception {
        CommandWord expectedCommandWord = new CommandWord("listf");
        assertEquals(expectedCommandWord, ParserUtil.parseCommandWord("listf"));
    }

    @Test
    public void parseCommandWord_validValueWithWhitespace_returnsTrimmedCommandWord() throws Exception {
        String commandWordWithWhitespace = WHITESPACE + "listf" + WHITESPACE;
        CommandWord expectedCommandWord = new CommandWord("listf");
        assertEquals(expectedCommandWord, ParserUtil.parseCommandWord(commandWordWithWhitespace));
    }
}
