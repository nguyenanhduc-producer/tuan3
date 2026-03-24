package org.example.tuan3.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "username", nullable = false, length = 100)
    private String username;

    public User() {
    }

    @Column(name = "password", nullable = false)
    private String password;

    // 2. Thêm liên kết nhiều - nhiều (Nhiều User có thể có Nhiều Role)
    // JPA sẽ tự động tạo ra cái bảng trung gian tên là "user_roles" cho bạn!
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_name")
    )
    private java.util.Set<Role> roles = new java.util.HashSet<>();

    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
