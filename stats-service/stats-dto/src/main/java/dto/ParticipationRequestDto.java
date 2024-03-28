package dto;

import lombok.*;

import javax.validation.constraints.Positive;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {
    private String created;

    @Positive(message = "event must be positive.")
    private Integer event;

    private Integer id;

    private Integer requester;

    private String status;

    @Override
    public String toString() {
        return "\n" +
                "ParticipationRequestDto{\n" +
                "                        created = " + created + "\n" +
                "                        event = " + event + "\n" +
                "                        id = " + id + "\n" +
                "                        requester = " + requester + "\n" +
                "                        status = " + status + "\n" +
                "                       }";
    }
}