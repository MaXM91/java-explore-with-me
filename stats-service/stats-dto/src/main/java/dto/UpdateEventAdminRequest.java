package dto;

import lombok.*;

import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventAdminRequest {
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

    private Integer participantLimit;

    private Boolean requestModeration;

    private String stateAction;

    @Size(min = 3, message = "min length title name 3.")
    @Size(max = 120, message = "max length title name 120.")
    private String title;
}
