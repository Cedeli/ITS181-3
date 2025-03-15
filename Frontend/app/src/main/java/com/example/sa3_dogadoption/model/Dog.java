package com.example.sa3_dogadoption.model;

import java.util.Objects;

public class Dog {

    private int id = -1;
    private String name;
    private String description;
    private String imagesrc;

    public Dog() {

    }
    public Dog(String name, String description, String imagesrc) {
        if (this.id != -1) {
            this.id += id;
        } else {
            this.id = 0;
        }
        this.name = name;
        this.description = description;
        this.imagesrc = imagesrc;
    }

    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return this.description;
    }
    public String getImagesrc() {
        return this.imagesrc;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.description);
        hash = 79 * hash + Objects.hashCode(this.imagesrc);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass())  return false;

        final Dog other = (Dog) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Dog{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", imagesrc='").append(imagesrc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
