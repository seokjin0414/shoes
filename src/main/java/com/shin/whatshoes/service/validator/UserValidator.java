package com.shin.whatshoes.service.validator;


import com.shin.whatshoes.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {

        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        User u = (User) obj;

        if (StringUtils.isEmpty(u.getUserId())) {
            errors.rejectValue("userId", "userId", "Please enter ID.");
        }
        if (StringUtils.isEmpty(u.getUserPw())) {
            errors.rejectValue("userPw", "userPw", "Please enter password.");
        }
        if (StringUtils.isEmpty(u.getUserName())) {
            errors.rejectValue("userName", "userName", "Please enter your name.");
        }
        if (StringUtils.isEmpty(u.getUserGender())) {
            errors.rejectValue("userGender", "userGender", "Please enter your gender.");
        }
//        if (StringUtils.isEmpty(u.getUserBirth().toString())) {
//            errors.rejectValue("userBirth", "userBirth", "Please enter your birthday.");
//        }

    }
}
