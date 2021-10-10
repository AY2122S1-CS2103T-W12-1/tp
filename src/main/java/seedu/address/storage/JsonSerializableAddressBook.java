package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.facility.Facility;
import seedu.address.model.person.Person;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_MEMBER = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedFacility> facilities = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons and facilities.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("facilities") List<JsonAdaptedFacility> facilities) {
        this.persons.addAll(persons);
        this.facilities.addAll(facilities);
    }



    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        facilities.addAll(source.getFacilityList().stream().map(JsonAdaptedFacility::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEMBER);
            }
            addressBook.addPerson(person);
        }

        for (JsonAdaptedFacility jsonAdaptedFacility : facilities) {
            Facility facility = jsonAdaptedFacility.toModelType();
            if (addressBook.hasFacility(facility)) {
                throw new IllegalValueException("DUPLICATE MEMBER MESSAGE TODO");
            }
            addressBook.addFacility(facility);
        }
        return addressBook;
    }

}
