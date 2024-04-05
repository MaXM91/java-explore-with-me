package ru.practicum.entity.request;

import lombok.*;
import ru.practicum.entity.event.Event;
import ru.practicum.entity.event.State;
import ru.practicum.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Request entity.
 * application for participation in the event
 * - created - date and time of application creation
 * - event - the event for which the application is being made
 * - requester - the creator of the application
 * - status - list of event lifecycle states
 */

@Entity
@Table(name = "request")
@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    @Column(name = "created")
    private LocalDateTime created;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private User requester;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private State status;

    @Override
    public String toString() {
        return "\n" +
                "      Request{\n" +
                "               created = " + created + "\n" +
                "               event = " + event + "\n" +
                "               id = " + id + "\n" +
                "               requester = " + requester + "\n" +
                "               status = " + status + "\n" +
                "              }";
    }
}