/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.model;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Jonathan Romualdo
 */
public class ValidadorBandeira implements ConstraintValidator<ValidaBandeira, String> {
    private List<String> bandeiras;
    
    @Override
    public void initialize(ValidaBandeira validabandeira) {
        this.bandeiras = new ArrayList<>();
        this.bandeiras.add("Visa");
        this.bandeiras.add("Master Card");
        this.bandeiras.add("Elo");
        this.bandeiras.add("Cielo");
        this.bandeiras.add("HiperCard");
    }

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {
        return valor == null ? false : bandeiras.contains(valor);
    }
    
}
