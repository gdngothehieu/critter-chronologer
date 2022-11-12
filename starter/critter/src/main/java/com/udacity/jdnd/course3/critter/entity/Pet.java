package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.pet.PetType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Pet {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id=0;

    private PetType type;
    private String name;
    private LocalDate birthDate;
    private String notes;

    @ManyToOne( cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer owner;

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public Pet() {
    }

    public Pet(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Pet(PetType type, String name, LocalDate birthDate, String notes, Customer owner) {
        this.type = type;
        this.name = name;
        this.birthDate = birthDate;
        this.notes = notes;
        this.owner = owner;
    }

    public Pet(long id, PetType type, String name, LocalDate birthDate, String notes, Customer owner) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.birthDate = birthDate;
        this.notes = notes;
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return id == pet.id && type == pet.type && Objects.equals(name, pet.name) && Objects.equals(birthDate, pet.birthDate) && Objects.equals(notes, pet.notes) && Objects.equals(owner, pet.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, name, birthDate, notes, owner);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
