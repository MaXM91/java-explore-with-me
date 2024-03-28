package ru.practicum.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * EndpointHit entity.
 *  - app - ID of the service for which information is being recorded
 *  - uri - the URI for which the request was made
 *  - ip - the IP address of the user who made the request
 *  - timestamp - the date and time when the request to the
 *  endpoints was made (in the format "yyyy-MM-dd HH:mm:ss")
 */
@Entity
@Table(name = "stats")
@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHit {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "app")
    private String app;

    @Column(name = "uri")
    private String uri;

    @Column(name = "ip")
    private String ip;

    @Column(name = "stamp")
    private LocalDateTime timestamp;

    @Override
    public String toString() {
        return "\n" +
                "    EndpointHit{\n" +
                "                id = " + id + "\n" +
                "                app = " + app + "\n" +
                "                uri = " + uri + "\n" +
                "                ip = " + ip + "\n" +
                "                timestamp = " + timestamp + "\n" +
                "               }";
    }
}