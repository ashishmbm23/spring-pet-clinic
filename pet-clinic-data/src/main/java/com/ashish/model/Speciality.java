package com.ashish.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table( name = "specialities" )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Speciality extends BaseEntity{

    @Column( name = "description" )
    private String description;

}
