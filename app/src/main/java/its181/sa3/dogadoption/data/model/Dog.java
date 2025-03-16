package its181.sa3.dogadoption.data.model;

public class Dog {
    private int id;
    private String name;
    private String breed;
    private String age;
    private String description;
    private boolean adopted;
    private int imageResourceId;

    public Dog(int id, String name, String breed, String age, String description, boolean adopted, int imageResourceId) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.description = description;
        this.adopted = adopted;
        this.imageResourceId = imageResourceId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public String getAge() {
        return age;
    }

    public boolean getAdopted() {
        return adopted;
    }

    public void setAdopted(boolean adopted) {
        this.adopted = adopted;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}