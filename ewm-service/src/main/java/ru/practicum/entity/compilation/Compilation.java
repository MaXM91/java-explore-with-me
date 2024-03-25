package ru.practicum.entity.compilation;

import lombok.*;
import ru.practicum.entity.event.Event;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "compilation")
@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Compilation {
    @ManyToMany
    @JoinTable(name = "compilation_events", joinColumns = @JoinColumn(name = "compilation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"))
    private List<Event> events;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "pinned")
    private Boolean pinned;

    @Column(name = "title")
    private String title;
}