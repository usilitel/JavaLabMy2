package lab.model.simple;

import lab.model.Squishee;
import lombok.Value;

@Value
public class SimpleSquishee implements Squishee {
    private String name;
}
