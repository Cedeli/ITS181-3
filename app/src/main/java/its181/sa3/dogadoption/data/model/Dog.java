package its181.sa3.dogadoption.data.model;

public class Dog {
    private Long id;
    private String name;
    private String breed;
    private String age;
    private String description;
    private boolean adopted;
    private String imageUrl;

    public Dog(Long id, String name, String breed, String age, String description, boolean adopted, String imageUrl) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.description = description;
        this.adopted = adopted;
        this.imageUrl = imageUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }

    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }

    public boolean getAdopted() { return adopted; }
    public void setAdopted(boolean adopted) { this.adopted = adopted; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}