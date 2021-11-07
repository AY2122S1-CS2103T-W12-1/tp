package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlySportsPa;
import seedu.address.model.SportsPa;
import seedu.address.model.facility.Facility;
import seedu.address.model.member.Member;

/**
 * An Immutable SportsPa that is serializable to JSON format.
 */
@JsonRootName(value = "sportspa")
class JsonSerializableSportsPa {

    public static final String MESSAGE_DUPLICATE_MEMBER = "Member list contains duplicate member(s).";
    public static final String MESSAGE_DUPLICATE_FACILITY = "Facility list contains duplicate facilities.";

    private final List<JsonAdaptedMember> members = new ArrayList<>();
    private final List<JsonAdaptedFacility> facilities = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableSportsPa} with the given members and facilities.
     */
    @JsonCreator
    public JsonSerializableSportsPa(@JsonProperty("members") List<JsonAdaptedMember> members,
                                    @JsonProperty("facilities") List<JsonAdaptedFacility> facilities) {
        this.members.addAll(members);
        this.facilities.addAll(facilities);
    }



    /**
     * Converts a given {@code ReadOnlySportsPa} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableSportsPa}.
     */
    public JsonSerializableSportsPa(ReadOnlySportsPa source) {
        members.addAll(source.getMemberList().stream().map(JsonAdaptedMember::new).collect(Collectors.toList()));
        facilities.addAll(source.getFacilityList().stream().map(JsonAdaptedFacility::new).collect(Collectors.toList()));
    }

    /**
     * Converts this SportsPA into the model's {@code SportsPa} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public SportsPa toModelType() throws IllegalValueException {
        SportsPa sportsPa = new SportsPa();
        for (JsonAdaptedMember jsonAdaptedMember : members) {
            Member member = jsonAdaptedMember.toModelType();
            if (sportsPa.hasMember(member)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEMBER);
            }
            sportsPa.addMember(member);
        }

        for (JsonAdaptedFacility jsonAdaptedFacility : facilities) {
            Facility facility = jsonAdaptedFacility.toModelType();
            if (sportsPa.hasFacility(facility)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FACILITY);
            }
            sportsPa.addFacility(facility);
        }
        return sportsPa;
    }

}
