package dto;

import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ViewStatsDto {
    private String app;

    private String uri;

    private Long hits;

    @Override
    public String toString() {
        return "\n" +
                "  ViewStatsDto{\n" +
                "                app = " + app + "\n" +
                "                uri = " + uri + "\n" +
                "                hits = " + hits + "\n" +
                "               }";
    }
}
