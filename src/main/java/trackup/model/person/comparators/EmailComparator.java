package trackup.model.person.comparators;

import trackup.model.person.Person;

import java.util.Comparator;

public class EmailComparator implements Comparator<Person> {

    @Override
    public int compare(Person person1, Person person2) {
        int EmailCompare = person1.getEmail().compareTo(
                person2.getEmail());
        return EmailCompare;
    }

}
