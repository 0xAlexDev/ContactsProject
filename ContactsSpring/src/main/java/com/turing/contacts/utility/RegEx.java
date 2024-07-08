package com.turing.contacts.utility;

public class RegEx {

    public static final String email = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    public static final String onlyLettes = "^[a-zA-Z]*$";

    public static final String startLettersAndNextNumbers = "^[a-zA-Z]+[a-zA-Z0-9]*$";

    public static final String lettersAndNumbers = "^[a-zA-Z0-9]*$";

    public static final String lettersAndNumbersWithSpaces = "^[a-zA-Z0-9 ]*$";

    public static final String onlyNumbers = "^[0-9]*$";

}
