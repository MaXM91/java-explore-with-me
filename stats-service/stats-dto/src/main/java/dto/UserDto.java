package dto;

import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id;

    private String email;

    private String name;

    @Override
    public String toString() {
        return "\n" +
                "       UserDto{\n" +
                "               id = " + id + "\n" +
                "               email = " + email + "\n" +
                "               name = " + name + "\n" +
                "              }";
    }
}
