package dto;

import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {
    private String annotation;

    private CategoryDto category;

    private Integer confirmedRequests;

    private String eventDate;

    private int id;

    private UserShortDto initiator;

    private boolean paid;

    private String title;

    private Integer views;

    @Override
    public String toString() {
        return  "\n" +
                "EventShortDto{\n" +
                "              annotation = " + annotation + "\n" +
                "              category = " + category + "\n" +
                "              confirmedRequests = " + confirmedRequests + "\n" +
                "              eventDate = " + eventDate + "\n" +
                "              id = " + id + "\n" +
                "              initiator = " + initiator + "\n" +
                "              paid = " + paid + "\n" +
                "              title = " + title + "\n" +
                "              views = " + views + "\n" +
                "              }";
    }
}