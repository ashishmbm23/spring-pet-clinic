package com.ashish.formatters;

import com.ashish.model.PetType;
import com.ashish.services.PetTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class PetTypeFormatter implements Formatter<PetType> {

    private final PetTypeService petTypeService;

    @Override
    public PetType parse(String name, Locale locale) throws ParseException {
        Collection<PetType> petTypeCollation = petTypeService.findAll();

        for (PetType petType: petTypeCollation) {
            if( petType.getName().equals(name)){
                return petType;
            }
        }
        throw new ParseException("Pet Type not present for name" + name, 0);
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }
}
