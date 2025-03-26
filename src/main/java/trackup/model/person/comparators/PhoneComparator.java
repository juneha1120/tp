package trackup.model.person.comparators;

import trackup.model.person.Person;

import java.util.Comparator;

public class PhoneComparator implements Comparator<Person> {

    @Override
    public int compare(Person person1, Person person2) {
        int PhoneCompare = person1.getPhone().compareTo(
                person2.getPhone());
        return PhoneCompare;
    }

}
