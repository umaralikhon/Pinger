package uz.ziraatbank.pinger.model.entity;

import lombok.*;
import uz.ziraatbank.pinger.config.Status;

import javax.persistence.*;

@Entity
@Table(name = "ports")
@Data
@NoArgsConstructor
public class Ports {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "service_name")
    private String serviceName;


    @Column(name = "host")
    private String host;

    @Column(name = "port")
    private Integer port;

    @Column(name = "attempt")
    private Integer attempt;

    @Column(name = "status")
    private Status status;
}
