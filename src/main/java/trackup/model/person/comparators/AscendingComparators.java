package trackup.model.person.comparators;

import trackup.model.person.Person;

import java.util.Comparator;

public class AscendingComparators {

    public static class NameComparator implements Comparator<Person> {

        @Override
        public int compare(Person person1, Person person2) {
            return person1.getName().compareTo(
                    person2.getName());
        }

    }

    public static class PhoneComparator implements Comparator<Person> {

        @Override
        public int compare(Person person1, Person person2) {
            return person1.getPhone().compareTo(
                    person2.getPhone());
        }

    }

    public static class AddressComparator implements Comparator<Person> {

        @Override
        public int compare(Person person1, Person person2) {
            return person1.getAddress().compareTo(
                    person2.getAddress());
        }

    }
    public static class EmailComparator implements Comparator<Person> {

        @Override
        public int compare(Person person1, Person person2) {
            return person1.getEmail().compareTo(
                    person2.getEmail());
        }

    }

    public static class TagComparator implements Comparator<Person> {

        @Override
        public int compare(Person person1, Person person2) {
            return person1.getTags().toString().compareTo(
                    person2.getTags().toString());
        }

    }

    public static class CategoryComparator implements Comparator<Person> {

        @Override
        public int compare(Person person1, Person person2) {
            return person1.getCategory().toString().compareTo(
                    person2.getCategory().toString());
        }

    }

}
