package example.pinger.model.entity;

import lombok.*;

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
    private Long id;

    private String serviceName;

    private String host;

    private Integer port;

    private Integer attempt;

    private Status status;

    private Double lastTimeout;

    private Boolean registered;

    private Source source;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ports", fetch = FetchType.EAGER)
    private List<PingTime> pingTime;

    public Ports(){
        this.pingTime = new ArrayList<>();
        this.attempt = 0;
        this.status = Status.DOWN;
        this.registered = false;
        this.source = Source.BUS;
    }


    public Ports(String serviceName, String host, Integer port){
        this.pingTime = new ArrayList<>();
        this.attempt = 0;
        this.status = Status.DOWN;
        this.lastTimeout = 0.0;
        this.registered = false;
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
