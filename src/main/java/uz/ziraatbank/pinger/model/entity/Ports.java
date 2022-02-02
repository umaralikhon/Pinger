package uz.ziraatbank.pinger.model.entity;

import lombok.*;
import uz.ziraatbank.pinger.config.Status;

import javax.persistence.*;

@Entity
@Table(name = "ports")
@Data
@AllArgsConstructor
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

    @Column(name = "time")
    private Double time;

    public Ports(){
        this.attempt = 0;
        this.status = Status.DOWN;
        this.time = 0.0;
    }


    public Ports(String serviceName, String host, Integer port){
        this();
        this.serviceName = serviceName;
        this.host = host;
        this.port = port;
    }
}
