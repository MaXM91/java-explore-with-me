package ru.practicum.entity.event;

import lombok.*;
import ru.practicum.entity.category.Category;
import ru.practicum.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Event entity.
 * - annotation - brief description of the event
 * - category - category of event
 * - confirmedRequests - confirmed request on this event
 * - createdOn - date and time of the event creation (in the format "yyyy-MM-dd HH:mm:ss")
 * - description - full description of the event
 * - eventDate - the date and time at which the event is scheduled.
 * The date and time are specified in the format "yyyy-MM-dd HH:mm:ss"
 * - initiator - the initiator of the event
 * Location:
 *   - lat - width
 *   - lon - longitude
 * - paid - do I need to pay for participation in the event
 * - participantLimit - limit on the number of participants.
 * A value of 0 means that there is no restriction
 * - publishedOn - date and time of publication of the event (in the format "yyyy-MM-dd HH:mm:ss")
 * - requestModeration - whether pre-moderation of applications for participation is necessary.
 * If true, all requests will be waiting for confirmation by the initiator of the event.
 * If false, they will be confirmed automatically.
 * - state - list of event lifecycle states:
 *   - PENDING,
 *   - PUBLISHED,
 *   - CANCELED
 * - title - event title
 */

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
