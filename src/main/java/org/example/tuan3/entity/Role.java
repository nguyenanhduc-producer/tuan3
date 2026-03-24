package org.example.tuan3.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @Column(name = "name", length = 50)
    private String name; // Tên quyền: USER, MANAGER

    @Column(name = "description")
    private String description;
}

