package dto;

import lombok.*;

import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCompilationRequest {
    private List<Integer> events;

    private Boolean pinned;

    @Size(min = 1, message = "min length compilation title 1.")
    @Size(max = 50, message = "max length compilation title 50.")
    private String title;

    @Override
    public String toString() {
        return "\n" +
                "UpdateCompilationRequest{\n" +
                "                         events = " + events + "\n" +
                "                         pinned = " + pinned + "\n" +
                "                         title = " + title + "\n" +
                "                        }";
    }
}