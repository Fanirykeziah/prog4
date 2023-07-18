package com.example.Prog4.controller.mapper;

import com.example.Prog4.model.Employe;
import com.example.Prog4.model.EmployeEntity;

public class EmployeMapper {
    public EmployeEntity toDomain(Employe employe){
        return EmployeEntity.builder()
                .id(employe.getId())
                .name(employe.getName())
                .photos("user-photos/" + employe.getPhotos().getOriginalFilename())
                .build();
    }
}
