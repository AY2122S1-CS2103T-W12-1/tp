package seedu.address.storage;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.member.Availability;
import seedu.address.model.member.Member;
import seedu.address.model.member.Name;
import seedu.address.model.member.Phone;
import seedu.address.model.member.TodayAttendance;
import seedu.address.model.member.TotalAttendance;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Member}.
 */
class JsonAdaptedMember {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Member's %s field is missing!";

    private final String name;
    private final String phone;
    private final List<DayOfWeek> availability;
    private final Boolean todayAttendance;
    private final Integer totalAttendance;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedMember} with the given member details.
     */
    @JsonCreator
    public JsonAdaptedMember(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("availability") List<DayOfWeek> availability,
                             @JsonProperty("todayAttendance") Boolean todayAttendance,
                             @JsonProperty("totalAttendance") Integer totalAttendance,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.availability = availability;
        this.todayAttendance = todayAttendance;
        this.totalAttendance = totalAttendance;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }

    }

    /**
     * Converts a given {@code Member} into this class for Jackson use.
     */
    public JsonAdaptedMember(Member source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        availability = source.getAvailability().values;
        todayAttendance = source.getTodayAttendance().isPresentToday();
        totalAttendance = source.getTotalAttendance().getAttendance();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted member object into the model's {@code Member} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted member.
     */
    public Member toModelType() throws IllegalValueException {
        final List<Tag> memberTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            memberTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (availability == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Availability.class.getSimpleName()));
        }
        final Availability modelAvailability = new Availability(availability);


        if (todayAttendance == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TodayAttendance.class.getSimpleName()));
        }
        final TodayAttendance modelTodayAttendance = new TodayAttendance(todayAttendance);

        if (totalAttendance == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TotalAttendance.class.getSimpleName()));
        }

        final TotalAttendance modelTotalAttendance = new TotalAttendance(totalAttendance);

        final Set<Tag> modelTags = new HashSet<>(memberTags);

        return new Member(modelName, modelPhone, modelAvailability, modelTodayAttendance,
                modelTotalAttendance, modelTags);
    }
}
