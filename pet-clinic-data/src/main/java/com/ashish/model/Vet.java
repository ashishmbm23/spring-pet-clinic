package com.ashish.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "vets" )
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vet extends Person{

    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable( name = "vet_speciality", joinColumns = @JoinColumn( name = "vet_id" ),
            inverseJoinColumns = @JoinColumn( name = "speciality_id" )
    )
    private Set<Speciality> specialities = new HashSet<>();

}
