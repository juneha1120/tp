package trackup.model.person.comparators;

import trackup.model.person.Person;

import java.util.Comparator;

public class NameComparator implements Comparator<Person> {

    @Override
    public int compare(Person person1, Person person2) {
        int NameCompare = person1.getName().compareTo(
                person2.getName());
        return NameCompare;
    }

}
