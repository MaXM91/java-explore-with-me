package dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class NewEventDto {
    @Size(min = 20, message = "min length annotation name 20.")
    @Size(max = 2000, message = "max length annotation name 2000.")
    @NotBlank(message = "annotation must be not blank/empty.")
    private String annotation;

    private Integer category;

    @Size(min = 20, message = "min length description name 20.")
    @Size(max = 7000, message = "max length description name 7000.")
    @NotBlank(message = "description must be not blank/empty.")
    private String description;

  //  @NotBlank(message = "event date must be yyyy-MM-dd HH:mm:ss format.")
    private String eventDate;

    private Location location;

    private boolean paid = false;

    private Integer participantLimit;

    private boolean requestModeration = true;

    @Size(min = 3, message = "min length title name 3.")
    @Size(max = 120, message = "max length title name 120.")
    @NotBlank(message = "title must be not blank/empty.")
    private String title;

    public NewEventDto(String annotation, Integer category, String description, String eventDate, Location location,
                       Boolean paid, Integer participantLimit, boolean requestModeration, String title) {
        this.annotation = annotation;
        this.category = category;
        this.description = description;
        this.eventDate = eventDate;
        this.location = location;
        this.paid = Objects.requireNonNullElse(paid, false);
        this.participantLimit = Objects.requireNonNullElse(participantLimit, 0);
        this.requestModeration = requestModeration;
        this.title = title;
    }

    public Integer getParticipantLimit() {
        return Objects.requireNonNullElse(participantLimit, 0);
    }
}