package dto;

import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {
    private String annotation;

    private CategoryDto category;

    private Integer confirmedRequests;

    private String createdOn;

    private String description;

    private String eventDate;

    private int id;

    private UserShortDto initiator;

    private Location location;

    private boolean paid;

    private Integer participantLimit;

    private String publishedOn;

    private boolean requestModeration;

    private String state;

    private String title;

    private Integer views;

    @Override
    public String toString() {
        return  "\n" +
                "EventFullDto{\n" +
                "             annotation = " + annotation + "\n" +
                "             category = " + category + "\n" +
                "             confirmedRequests = " + confirmedRequests + "\n" +
                "             createdOn = " + createdOn + "\n" +
                "             description = " + description + "\n" +
                "             eventDate = " + eventDate + "\n" +
                "             id = " + id + "\n" +
                "             initiator = " + initiator + "\n" +
                "             location = " + location + "\n" +
                "             paid = " + paid + "\n" +
                "             participantLimit = " + participantLimit + "\n" +
                "             publishedOn = " + publishedOn + "\n" +
                "             requestModeration = " + requestModeration + "\n" +
                "             state = " + state + "\n" +
                "             title = " + title + "\n" +
                "             views = " + views + "\n" +
                "            }";
    }
}
