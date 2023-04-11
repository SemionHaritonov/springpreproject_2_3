package ru.stud.homer.springpreproject.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.stud.homer.springpreproject.models.User;
import ru.stud.homer.springpreproject.services.UserService;

@Component
public class UserCreationValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserCreationValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userService.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "", "Email is already use");
        }
    }
}
