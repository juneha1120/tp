package trackup.model.person.comparators;

import trackup.model.person.Person;

import java.util.Comparator;

public class DescendingComparators {

    public static class NameComparator implements Comparator<Person> {

        @Override
        public int compare(Person person1, Person person2) {
            return person2.getName().compareTo(
                    person1.getName());
        }

    }

    public static class PhoneComparator implements Comparator<Person> {

        @Override
        public int compare(Person person1, Person person2) {
            return person2.getPhone().compareTo(
                    person1.getPhone());
        }

    }

    public static class AddressComparator implements Comparator<Person> {

        @Override
        public int compare(Person person1, Person person2) {
            return person2.getAddress().compareTo(
                    person1.getAddress());
        }

    }
    public static class EmailComparator implements Comparator<Person> {

        @Override
        public int compare(Person person1, Person person2) {
            return person2.getEmail().compareTo(
                    person1.getEmail());
        }

    }

    public static class TagComparator implements Comparator<Person> {

        @Override
        public int compare(Person person1, Person person2) {
            return person2.getTags().toString().compareTo(
                    person1.getTags().toString());
        }

    }

    public static class CategoryComparator implements Comparator<Person> {

        @Override
        public int compare(Person person1, Person person2) {
            return person2.getCategory().toString().compareTo(
                    person1.getCategory().toString());
        }

    }

}
