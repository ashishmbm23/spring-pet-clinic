package com.ashish.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table( name = "types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetType extends BaseEntity{

    @Column( name = "name")
    private String name;

    @Builder
    public PetType(Long id, String name){
        super(id);
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
