package com.ashish.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table( name = "visits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Visit extends BaseEntity{

    @Column( name = "visit_date" )
    private LocalDate date;

    @Column( name = "description" )
    private String description;

    @ManyToOne
    @JoinColumn( name = "pet_id" )
    private Pet pet;

}
