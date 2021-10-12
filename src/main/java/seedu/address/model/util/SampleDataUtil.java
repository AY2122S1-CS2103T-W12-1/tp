package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.facility.Capacity;
import seedu.address.model.facility.Facility;
import seedu.address.model.facility.FacilityName;
import seedu.address.model.facility.Location;
import seedu.address.model.facility.Time;
import seedu.address.model.person.Availability;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Availability SAMPLE_AVAILABILITY = new Availability("MON WED FRI");

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), SAMPLE_AVAILABILITY),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), SAMPLE_AVAILABILITY),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), SAMPLE_AVAILABILITY),
            new Person(new Name("David Li"), new Phone("91031282"), SAMPLE_AVAILABILITY),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), SAMPLE_AVAILABILITY),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), SAMPLE_AVAILABILITY)
        };
    }

    public static Facility[] getSampleFacilities() {
        return new Facility[] {
            new Facility(new FacilityName("Court 1"), new Location("University Sports Hall"),
                    new Time("1130"), new Capacity("5")),
            new Facility(new FacilityName("NUS Field 2"), new Location("Opp University Hall"),
                    new Time("1330"), new Capacity("8")),
            new Facility(new FacilityName("Court 2"), new Location("University Sports Hall"),
                    new Time("2030"), new Capacity("10")),
            new Facility(new FacilityName("Court 3"), new Location("University Sports Hall"),
                    new Time("1230"), new Capacity("6"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }

        for (Facility sampleFacility : getSampleFacilities()) {
            sampleAb.addFacility(sampleFacility);
        }

        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
