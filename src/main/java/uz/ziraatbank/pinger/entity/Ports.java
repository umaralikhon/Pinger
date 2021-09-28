package uz.ziraatbank.pinger.entity;

import lombok.Data;
import lombok.Generated;

import javax.persistence.*;

@Entity
@Table(name = "ports")
@Data
public class Ports {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ip")
    private String ip;

    @Column(name = "port")
    private String port;

    @Column(name = "service_id")
    private String serviceId;

    @Column(name = "subservice")
    private String subservice;

//    @ManyToOne(fetch = FetchType.LAZY)
//            @JoinColumn(name = "services_id", nullable = false)
//    Services services;
}
