package trackup.model.person.comparators;

import trackup.model.person.Person;

import java.util.Comparator;

public class CategoryComparator implements Comparator<Person> {

    @Override
    public int compare(Person person1, Person person2) {
        int CategoryCompare = person1.getCategory().toString().compareTo(
                person2.getCategory().toString());
        return CategoryCompare;
    }

}