package ru.example.udinkirill.model.validations;


import org.springframework.beans.factory.annotation.Autowired;
import ru.example.udinkirill.services.impl.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class IsUsernameTakenValidator implements ConstraintValidator<IsUsernameTaken, String> {
    @Autowired
    private UserService userService;


    @Override
    public void initialize(IsUsernameTaken isUsernameTaken) {

    }

    @Override public boolean isValid(String value, ConstraintValidatorContext context) {

        return !this.userService.isUsernameTaken(value);
    }

}
