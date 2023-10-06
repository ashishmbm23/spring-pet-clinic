package com.ashish.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table( name = "types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetType extends BaseEntity{

    @Column( name = "name")
    private String name;

    @Override
    public String toString() {
        return this.getName();
    }
}
