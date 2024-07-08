package com.turing.contacts.interfaces;

public interface EntityMapperInterface<Entity,DTO> {
    DTO toDTO (Entity entity);
    Entity toEntity (DTO dto);
}
