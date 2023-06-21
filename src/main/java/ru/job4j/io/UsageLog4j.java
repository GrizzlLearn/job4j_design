package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsageLog4j {
    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) {
        String firstName = "Dmitry";
        String lastName = "Petrov";
        int ageInt = 25;
        byte ageByte = 25;
        short ageInMonths = 300;
        char sex = 'M';
        boolean isMale = true;
        long birthdateInUnixTime = 582904688;
        float ageFloat = 25.571f;
        double ageDouble = 25.57145634561865;
        LOG.debug("User info: firstName: {}, lastName: {}, age: {}, ageInByte: {}, ageInMonths: {}, sex: {}, isMale: {}, birthdateInUnixTime: {}, ageFloat: {}, ageDouble: {}",
                firstName,
                lastName,
                ageInt,
                ageByte,
                ageInMonths,
                sex,
                isMale,
                birthdateInUnixTime,
                ageFloat,
                ageDouble
        );
    }
}
