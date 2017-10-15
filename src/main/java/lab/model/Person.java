package lab.model;

import java.util.List;

public interface Person {
    String getName ();
    int getId();
    Country getCountry();
    int getAge();
    float getHeight();
    boolean isProgrammer();

    List<String> getContacts();

    default void sayHello(Person person) {
        System.out.printf("Hello, %s, I`m %s!",
                person.getName(),
                getName());
    }
}
