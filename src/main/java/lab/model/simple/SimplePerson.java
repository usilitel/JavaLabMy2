package lab.model.simple;

import lab.model.Country;
import lab.model.Person;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Value
public class SimplePerson implements Person {
    private int id;
    private String name;
    private Country country;

    private int age;
    private float height;
    private boolean isProgrammer;

    private List<String> contacts;
}