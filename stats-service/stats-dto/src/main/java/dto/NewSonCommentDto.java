package dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class NewSonCommentDto {
    @Size(min = 1, message = "min length comment text 1.")
    @Size(max = 500, message = "max length comment text 500.")
    @NotBlank(message = "text must not be blank/empty.")
    private String text;

    @Positive(message = "responseToUser must be positive.")
    private Integer responseToUser;

    @Override
    public String toString() {
        return  "\n" +
                "NewCommentDto{" + "\n" +
                "             text = " + text + "\n" +
                "             responseToUser = " + responseToUser + "\n" +
                "             }";
    }
}