package com.vaadin.tests.validation;

import org.junit.Test;

import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.ui.TextField;

public class TestReadOnlyValidation {

    @Test
    public void testIntegerValidation() {
        TextField field = new TextField();
        field.addValidator(new IntegerValidator("Enter a Valid Number"));
        field.setValue(Integer.valueOf(10));
        field.validate();
    }
}