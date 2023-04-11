package ru.stud.homer.springpreproject.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.stud.homer.springpreproject.models.User;
import ru.stud.homer.springpreproject.services.UserService;

import java.util.Objects;

@Component
public class UserUpdatingValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserUpdatingValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        User fundedUser = userService.findByEmail(user.getEmail());
        if (fundedUser != null && !Objects.equals(fundedUser.getId(), user.getId())) {
            errors.rejectValue("email", "", "Email is already use");
        }
    }
}
