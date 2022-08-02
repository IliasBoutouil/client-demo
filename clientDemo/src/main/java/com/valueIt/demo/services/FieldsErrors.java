package com.valueIt.demo.services;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

public class FieldsErrors {
    private FieldsErrors()
    {

    }
    public static List<String> getFieldsWithErrors(BindingResult bindingResult)
    {
        List<String> errors=new ArrayList<>();
        if(bindingResult.hasErrors())
        {
            bindingResult.getFieldErrors().forEach(error->
                    errors.add(error.getField()+" "+error.getDefaultMessage())
            );
        }
        return errors;
    }
}
