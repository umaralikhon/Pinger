package uz.ziraatbank.pinger.model.entity;

import lombok.*;
import uz.ziraatbank.pinger.config.Status;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    private Double lastTimeout;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ports", fetch = FetchType.EAGER)
    private List<PingTime> pingTime;

    public Ports(){
        this.pingTime = new ArrayList<>();
        this.attempt = 0;
        this.status = Status.DOWN;
    }


    public Ports(String serviceName, String host, Integer port){
        this.pingTime = new ArrayList<>();
        this.attempt = 0;
        this.status = Status.DOWN;
        this.lastTimeout = 0.0;
        this.serviceName = serviceName;
        this.host = host;
        this.port = port;
    }

    public void addPingTimeToPorts(PingTime pingTime){
        if(this.pingTime == null){
            this.pingTime = new ArrayList<>();
        }

        this.pingTime.add(pingTime);
        pingTime.setPorts(this);
    }
}
