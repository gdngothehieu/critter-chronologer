package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    UserService userService;

    @Autowired
    public PetController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = userService.savePet(convertDTOtoPet(petDTO));
        return convertPettoDTO(pet);
    }

    @PostMapping("/{petId}")
    public PetDTO savePetwithId(@RequestBody PetDTO petDTO) {
        Pet pet = userService.savePet(convertDTOtoPet(petDTO));
        return convertPettoDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertPettoDTO(userService.getPetById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetDTO> allPetDTO = new ArrayList<>();
        List<Pet> allPets = userService.getAllPets();
        for(Pet p: allPets){
            allPetDTO.add(convertPettoDTO(p));
        }
        return allPetDTO;

    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> allPets = userService.getAllPets();
        List<PetDTO> allPetDTO = new ArrayList<>();
        for(Pet p: allPets){
            allPetDTO.add(convertPettoDTO(p));
        }
        return allPetDTO;
    }

    private Pet convertDTOtoPet(PetDTO petDTO){
        Pet pet = new Pet();
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setType(petDTO.getType());
        pet.setNotes(petDTO.getNotes());
        pet.setId(petDTO.getId());
        if(petDTO.getOwnerId()!=0){
            pet.setOwner(userService.getCustomerById(petDTO.getOwnerId()));
        }else{
            System.out.println("Pet "+petDTO + "doesn't have a owner");
            pet.setOwner(null);
        }

        return pet;
    }

    private PetDTO convertPettoDTO(Pet pet){
        System.out.println("Inside private PetDTO convertPettoDTO(Pet pet)");
        System.out.println("pet:="+pet);
        PetDTO petDTO= new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setName(pet.getName());
        petDTO.setNotes(pet.getNotes());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setType(pet.getType());
        if(pet.getOwner()!=null){
            petDTO.setOwnerId(pet.getOwner().getId());
        }else{
            petDTO.setOwnerId(0);
        }

        return petDTO;
    }


}
