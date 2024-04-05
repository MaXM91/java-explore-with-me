package dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Integer id;

    @Size(min = 1, message = "min length category name 1.")
    @Size(max = 50, message = "max length category name 50.")
    @NotBlank(message = "сategory name empty/null.")
    private String name;

    @Override
    public String toString() {
        return "\n" +
                "   CategoryDto{\n" +
                "               id = " + id + "\n" +
                "               name = " + name + "\n" +
                "              }";
    }
}
