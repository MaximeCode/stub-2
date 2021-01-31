package ru.appline.logic.pets;

public class Pet {
    private String name;
    private String type;
    private int age;

    public Pet() {
        super();
    }

    public Pet(String name, String type, int age) {
        this.name = name;
        this.type = type;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Pet setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public Pet setType(String type) {
        this.type = type;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Pet setAge(int age) {
        this.age = age;
        return this;
    }
}
