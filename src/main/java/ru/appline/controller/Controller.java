package ru.appline.controller;

import org.springframework.web.bind.annotation.*;
import ru.appline.logic.compass.CompassModel;
import ru.appline.logic.pets.Pet;
import ru.appline.logic.pets.PetModel;

import java.util.Map;
import java.util.Set;

/**
 * Стартует на http://localhost:8081/
 */
@RestController
public class Controller {
    private static final PetModel petModel = PetModel.getInstance();
    private static final CompassModel compassModel = CompassModel.getInstance();

    /**
     * @example {"name": "Sharik", "type": "dog", "age": 5}
     * @example {"name": "Barsik", "type": "cat", "age": 4}
     */
    @PostMapping(value = "/createPet", consumes = "application/json")
    public String createPet(@RequestBody Pet pet) {
        petModel.add(pet);
        return "Поздравляем! Вы создали " + (petModel.getPets().size() == 1 ? "своего первого " : "") + "питомца!";
    }

    @GetMapping(value = "/getAllPets", consumes = "application/json")
    public Map<Integer, Pet> getAllPets() {
        return petModel.getPets();
    }

    /**
     * @example {"id": 1}
     */
    @GetMapping(value = "/getPet", consumes = "application/json")
    public Pet getPet(@RequestBody Map<String, Integer> id) {
        return petModel.get(id.get("id"));
    }

    /**
     * @example {"id": 1}
     */
    @DeleteMapping(value = "/deletePet", consumes = "application/json")
    public void deletePet(@RequestBody Map<String, Integer> id) {
        petModel.remove(id.get("id"));
    }

    /**
     * @example {"id": 1, "name": "Kesha", "type": "bird", "age": 2}
     */
    @PutMapping(value = "/updatePet", consumes = "application/json")
    public void updatePet(@RequestBody Map<String, String> pet) {
        petModel.update(Integer.parseInt(pet.get("id")), pet.get("name"), pet.get("type"), Integer.parseInt(pet.get("age")));
    }

    /**
     * @example {"North": "338-22", "North-East": "23-67", "East": "68-112", "South-East": "113-157", "South": "158-202", "South-West": "203-247", "West": "248-292", "North-West": "293-337"}
     */
    @PostMapping(value = "/setDirections", consumes = "application/json")
    public Set<Map<String, String>> setDirections(@RequestBody Map<String, String> directions) {
        compassModel.setDirections(directions);
        return compassModel.getDirections();
    }

    /**
     * @example {"Degree": 123}
     */
    @GetMapping(value = "/getDirection", consumes = "application/json")
    public Map<String, String> getDirection(@RequestBody Map<String, Integer> degree) {
        return compassModel.getDirection(degree.get("Degree"));
    }
}
