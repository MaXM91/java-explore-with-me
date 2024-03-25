package dto;

import lombok.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventUserRequest {
    @Size(min = 20, message = "min length annotation name 20.")
    @Size(max = 2000, message = "max length annotation name 2000.")
    private String annotation;

    private Integer category;

    @Size(min = 20, message = "min length description name 20.")
    @Size(max = 7000, message = "max length description name 7000.")
    private String description;

    private String eventDate;

    private Location location;

    private Boolean paid;

    @Positive(message = "participantLimit must be positive or null.")
    private Integer participantLimit;

    private Boolean requestModeration;

    private String stateAction;

    @Size(min = 3, message = "min length title name 3.")
    @Size(max = 120, message = "max length title name 120.")
    private String title;

    @Override
    public String toString() {
        return "\n" +
                "UpdateEventUserRequest{\n" +
                "                       annotation = " + annotation + "\n" +
                "                       category = " + category + "\n" +
                "                       description = " + description + "\n" +
                "                       eventDate = " + eventDate + "\n" +
                "                       location = " + location + "\n" +
                "                       paid = " + paid + "\n" +
                "                       participantLimit = " + participantLimit + "\n" +
                "                       requestModeration = " + requestModeration + "\n" +
                "                       stateAction = " + stateAction + "\n" +
                "                       title = " + title + "\n" +
                "                      }";
    }
}