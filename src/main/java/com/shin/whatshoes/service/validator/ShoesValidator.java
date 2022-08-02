package com.shin.whatshoes.service.validator;

import com.shin.whatshoes.model.Shoes;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component
public class ShoesValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Shoes.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Shoes s = (Shoes) obj;
        if (StringUtils.isEmpty(s.getBrandId())) {
            errors.rejectValue("brandId", "brandId", "Please choose a brand.");
        }
    }
}
