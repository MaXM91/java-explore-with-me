package dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {
    private List<Integer> events;

    private Boolean pinned;

    @Size(min = 1, message = "min length compilation title 1.")
    @Size(max = 50, message = "max length compilation title 50.")
    @NotBlank(message = "title must not be blank/empty.")
    private String title;

    @Override
    public String toString() {
        return "\n" +
                " NewCompilationDto{\n" +
                "                   events = " + events + "\n" +
                "                   pinned = " + pinned + "\n" +
                "                   title = " + title + "\n" +
                "                  }";
    }
}
