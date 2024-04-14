package dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

    private List<CommentDto> commentsList;

    @Override
    public String toString() {
        return  "  CommentDto{" + "\n" +
                "             id = " + id + "\n" +
                "             text = " + text + "\n" +
                "             registrationTime = " + registrationTime + "\n" +
                "                 owner = " + owner + "\n" +
                "                 responseToUser = " + responseToUser + "\n" +
                "                         commentsList = " + commentsList + "\n" +
                "             }";
    }
}