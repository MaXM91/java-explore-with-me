package dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHitDto {
    private Long id;

    @NotBlank(message = "app should be not blank/empty")
    private String app;

    @NotBlank(message = "uri should be not blank/empty")
    private String uri;

    @NotBlank(message = "ip should be not blank/empty")
    private String ip;

    @NotNull(message = "timestamp should not be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

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
