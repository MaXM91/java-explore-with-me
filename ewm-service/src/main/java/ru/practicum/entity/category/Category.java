package ru.practicum.entity.category;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;

/**
 * Category entity.
 * - name - category name,
 * must be unique
 */

@Entity
@Table(name = "categories")
@Builder
@Getter
@Service
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Override
    public String toString() {
        return "\n" +
                "      Category{\n" +
                "               id = " + id + "\n" +
                "               name = " + name + "\n" +
                "              }";
    }
}
