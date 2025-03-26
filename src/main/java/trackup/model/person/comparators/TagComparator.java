package trackup.model.person.comparators;

import trackup.model.person.Person;

import java.util.Comparator;

public class TagComparator implements Comparator<Person> {

    @Override
    public int compare(Person person1, Person person2) {
        int TagCompare = person1.getTags().toString().compareTo(
                person2.getTags().toString());
        return TagCompare;
    }

}

