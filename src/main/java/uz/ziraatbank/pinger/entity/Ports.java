package uz.ziraatbank.pinger.entity;

import lombok.*;
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

    @Column(name = "ip")
    private String ip;

    @Column(name = "port")
    private int port;

    @Column(name = "subservice")
    private String subservice;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    private Services services;

    public Ports(String ip, int port, String subservice) {
        this.ip = ip;
        this.port = port;
        this.subservice = subservice;
    }
}
