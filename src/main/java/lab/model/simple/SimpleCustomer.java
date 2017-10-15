package lab.model.simple;

import lab.model.Customer;
import lab.model.Person;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.Delegate;

@Value
public class SimpleCustomer implements Customer {

    @Delegate
    @Getter(AccessLevel.NONE)
    private Person person;

    private boolean broke;
}
