package dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHitDto {
    private Integer id;

    @NotBlank(message = "app should be not blank/empty.")
    private String app;

    @NotBlank(message = "uri should be not blank/empty.")
    private String uri;

    @NotBlank(message = "ip should be not blank/empty.")
    private String ip;

    @NotNull(message = "timestamp should not be null.")
    private String timestamp;

    @Override
    public String toString() {
        return "\n" +
                " EndpointHitDto{\n" +
                "                id = " + id + "\n" +
                "                app = " + app + "\n" +
                "                uri = " + uri + "\n" +
                "                ip = " + ip + "\n" +
                "                timestamp = " + timestamp + "\n" +
                "               }";
    }
}