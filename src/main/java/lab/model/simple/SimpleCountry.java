package lab.model.simple;

import lab.model.Country;
import lombok.Value;

@Value
public class SimpleCountry implements Country {
	private int id;
    private String name;
    private String codeName;
}
