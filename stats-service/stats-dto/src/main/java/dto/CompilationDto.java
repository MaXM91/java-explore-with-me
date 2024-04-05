package dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    private List<EventShortDto> events;

    private int id;

    private Boolean pinned;

    private String title;

    @Override
    public String toString() {
        return "\n" +
                " CompilationDto{\n" +
                "                events = " + events + "\n" +
                "                id = " + id + "\n" +
                "                pinned = " + pinned + "\n" +
                "                title = " + title + "\n" +
                "               }";
    }
}
