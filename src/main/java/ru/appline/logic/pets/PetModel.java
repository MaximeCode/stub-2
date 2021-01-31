package ru.appline.logic.pets;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class PetModel implements Serializable {
    private static PetModel instance;
    private final Map<Integer, Pet> pets = new HashMap<Integer, Pet>();
    private final AtomicInteger counter = new AtomicInteger();

    public PetModel() {
    }

    public static PetModel getInstance() {
        if (instance == null) {
            instance = new PetModel();
        }
        return instance;
    }

    public Map<Integer, Pet> getPets() {
        return pets;
    }

    public void add(Pet pet) {
        pets.put(counter.incrementAndGet(), pet);
    }

    public Pet get(int id) {
        return pets.get(id);
    }

    public void remove(int id) {
        pets.remove(id);
    }

    public void update(int id, String name, String type, int age) {
        pets.get(id)
                .setName(name)
                .setType(type)
                .setAge(age);
    }
}
