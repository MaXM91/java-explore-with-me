package dto;

import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserShortDto {
    private int id;

    private String name;

    @Override
    public String toString() {
        return "\n" +
                "  UserShortDto{\n" +
                "               id = " + id + "\n" +
                "               name = " + name + "\n" +
                "              }";
    }
}
