package ru.practicum.entity.comment;

import lombok.*;
import ru.practicum.entity.event.Event;
import ru.practicum.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Comment entity.
 * - text - the text of the comment is no more than 500 characters
 * - registrationTime - datetime the comment was created
 * - owner - the user who wrote the comment
 * - responseToUser - the user whose comment was answered
 * - event - the event under discussion
 * - commentsList - comments in response to this comment
 */

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = true)
    private Event event;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "comments_events", joinColumns = @JoinColumn(name = "father_comment_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "son_comment_id", referencedColumnName = "id"))
    private List<Comment> commentsList;

    public void setComment(Comment comment) {
        commentsList.add(comment);
    }

    @Override
    public String toString() {
        return  "\n" +
                "     Comment{" + "\n" +
                "             id = " + id + "\n" +
                "             text = " + text + "\n" +
                "             registrationTime = " + registrationTime + "\n" +
                "                 ownerComment = " + owner + "\n" +
                "                         responseToUser = " + responseToUser + "\n" +
                "             event = " + event + "\n" +
                "                         commentsList = " + commentsList + "\n" +
                "             }";
    }
}