package trackup.model.person.comparators;

import trackup.model.person.Person;

import java.util.Comparator;

import java.util.function.Function;

public class AscendingComparators {

    private static <T extends Comparable<T>> Comparator<Person> comparing(Function<Person, T> keyExtractor) {
        return Comparator.comparing(keyExtractor);
    }

    public static final Comparator<Person> NAME_COMPARATOR = comparing(Person::getName);
    public static final Comparator<Person> PHONE_COMPARATOR = comparing(Person::getPhone);
    public static final Comparator<Person> ADDRESS_COMPARATOR = comparing(Person::getAddress);
    public static final Comparator<Person> EMAIL_COMPARATOR = comparing(Person::getEmail);
    public static final Comparator<Person> TAG_COMPARATOR = comparing(p -> p.getTags().toString());
    public static final Comparator<Person> CATEGORY_COMPARATOR = comparing(p -> p.getCategory().toString());

}
