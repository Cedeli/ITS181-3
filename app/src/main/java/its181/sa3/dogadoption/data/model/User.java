package its181.sa3.dogadoption.data.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {
    private Long id;
    private String email;
    private String password;
    private String role;
    private List<Dog> adoptedDogs = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public List<Dog> getAdoptedDogs() { return adoptedDogs; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
