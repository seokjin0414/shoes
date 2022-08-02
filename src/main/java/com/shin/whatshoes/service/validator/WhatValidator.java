package com.shin.whatshoes.service.validator;

import com.shin.whatshoes.model.What;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class WhatValidator  implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return What.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        What w = (What) obj;

        errors.rejectValue("whatFileName", "whatFileName", "Please select a file of image type.");

    }
}
