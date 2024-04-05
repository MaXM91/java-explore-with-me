package ru.practicum.entity.user;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;

/**
 * User entity.
 * - email - email of user, must be unique
 * - name - name of user
 */

@Entity
@Table(name = "users")
@Builder
@Getter
@Service
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Override
    public String toString() {
        return "\n" +
                "          User{\n" +
                "               id = " + id + "\n" +
                "               email = " + email + "\n" +
                "               name = " + name + "\n" +
                "              }";
    }
}
