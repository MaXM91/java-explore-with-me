package ru.practicum.entity.event;

import lombok.*;
import ru.practicum.entity.category.Category;
import ru.practicum.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "event")
@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Column(name = "annotation")
    private String annotation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "confirmed_requests")
    private Integer confirmedRequests;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "description")
    private String description;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User initiator;

    @Column(name = "lat")
    private double lat;

    @Column(name = "lon")
    private double lon;

    @Getter
    @Setter
    @Column(name = "paid")
    private boolean paid;

    @Column(name = "participant_limit")
    private Integer participantLimit;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Getter
    @Setter
    @Column(name = "request_moderation")
    private boolean requestModeration;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "state")
    private State state;

    @Column(name = "title")
    private String title;

    @Override
    public String toString() {
        return  "\n" +
                "      Event{\n" +
                "            annotation = " + annotation + "\n" +
                "            category = " + category + "\n" +
                "            confirmedRequests = " + confirmedRequests + "\n" +
                "            createdOn = " + createdOn + "\n" +
                "            description = " + description + "\n" +
                "            eventDate = " + eventDate + "\n" +
                "            id = " + id + "\n" +
                "            initiator = " + initiator + "\n" +
                "            lat = " + lat + "\n" +
                "            lon = " + lon + "\n" +
                "            paid = " + paid + "\n" +
                "            participantLimit = " + participantLimit + "\n" +
                "            publishedOn = " + publishedOn + "\n" +
                "            requestModeration = " + requestModeration + "\n" +
                "            state = " + state + "\n" +
                "            title = " + title + "\n" +
                "            }";
    }
}
