package com.turing.contacts.interfaces;

public interface ControllerValidator<DTO> {
    public Boolean updateValidation(DTO dto);
    public Boolean getValidation(DTO dto);
    public Boolean createValidation(DTO dto);
    public Boolean deleteValidation(DTO dto);
} 
