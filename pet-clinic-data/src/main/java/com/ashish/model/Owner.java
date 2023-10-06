package com.ashish.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "owners")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Owner extends Person implements Comparable<Owner>{

    @Builder
    public Owner( Long id, String firstName, String lastName, String address, String city, String telephone, Set<Pet> pets){
        super(id, firstName, lastName);
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.pets = pets;
    }

    @Column( name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column( name = "telephone" )
    private String telephone;

    @OneToMany( cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Pet> pets = new HashSet<Pet>();

    @Override
    public int compareTo(Owner o) {
        if( this.getId() < o.getId() ){
            return -1;
        }else{
            return 1;
        }
    }
}
