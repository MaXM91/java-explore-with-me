package ru.practicum.entity.comment;

import lombok.*;
import ru.practicum.entity.event.Event;
import ru.practicum.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "comments")
@Entity
@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "text")
    private String text;

    @Column(name = "registration_time")
    private LocalDateTime registrationTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "response_to_id")
    private User responseToUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    private Event event;

    @Override
    public String toString() {
        return  "\n" +
                "     Comment{" + "\n" +
                "             id = " + id + "\n" +
                "             text = " + text + "\n" +
                "             registrationTime = " + registrationTime + "\n" +
                "             ownerComment = " + owner + "\n" +
                "             responseToUser = " + responseToUser + "\n" +
                "             event = " + event + "\n" +
                "             }";
    }
}