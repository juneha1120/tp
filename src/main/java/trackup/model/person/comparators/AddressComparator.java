package trackup.model.person.comparators;

import trackup.model.person.Person;

import java.util.Comparator;

public class AddressComparator implements Comparator<Person> {

    @Override
    public int compare(Person person1, Person person2) {
        int AddressCompare = person1.getAddress().compareTo(
                person2.getAddress());
        return AddressCompare;
    }

}
