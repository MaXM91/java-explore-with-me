package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewStatsDto {
    private String app;

    private String uri;

    private Integer hits;

    public ViewStatsDto(String app, String uri, String hits) {
        this.app = app;
        this.uri = uri;
        this.hits = Integer.parseInt(hits);
    }

    public ViewStatsDto(String app, String uri, Long hits) {
        this.app = app;
        this.uri = uri;
        this.hits = Integer.parseInt(String.valueOf(hits));
    }

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
