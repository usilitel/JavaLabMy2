package lab.model;

import lombok.SneakyThrows;

import java.lang.reflect.Field;

public interface Customer extends Person {

    boolean isBroke();

    /** WARN: Method for test-only uses only! Don`t use it for business-logic */
    @SneakyThrows
    default Person setBroke(boolean broke) {
        Field brokeField = this.getClass().getField("broke");
        brokeField.setAccessible(true);
        brokeField.set(this, broke);
        return this;
    }
}
