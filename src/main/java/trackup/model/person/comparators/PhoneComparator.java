package trackup.model.person.comparators;

import trackup.model.person.Person;

import java.util.Comparator;

public class PhoneComparator implements Comparator<Person> {

    @Override
    public int compare(Person person1, Person person2) {
        int PhoneCompare = person2.getPhone().compareTo(
                person1.getPhone());
        return PhoneCompare;
    }

}
