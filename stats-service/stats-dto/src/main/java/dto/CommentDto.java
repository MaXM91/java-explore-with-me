package dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private int id;

    private String text;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registrationTime;

    private UserDto owner;

    private UserDto responseToUser;

    @Override
    public String toString() {
        return  "\n" +
                "     Comment{" + "\n" +
                "             id = " + id + "\n" +
                "             text = " + text + "\n" +
                "             registrationTime = " + registrationTime + "\n" +
                "             owner = " + owner + "\n" +
                "             responseToUser = " + responseToUser + "\n" +
                "             }";
    }
}
