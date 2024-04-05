package dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class NewUserRequest {
    @Size(min = 6, message = "min length email name 6.")
    @Size(max = 254, message = "max length email name 254.")
    @NotBlank(message = "blank/empty email.")
    @Email(message = "bad user email.")
    private String email;

    @Size(min = 2, message = "min length user name 2.")
    @Size(max = 250, message = "max length user name 250.")
    @NotBlank(message = "user name empty/null.")
    private String name;

    @Override
    public String toString() {
        return "\n" +
                "NewUserRequest{\n" +
                "               email = " + email + "\n" +
                "               name = " + name + "\n" +
                "              }";
    }
}
