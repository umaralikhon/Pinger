package uz.ziraatbank.pinger.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class PingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime time;

    private Double timeout;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ports_id")
    private Ports ports;

    public PingTime(){
        this.time = LocalDateTime.now();
        timeout = 0.0;
    }

    public PingTime(LocalDateTime time, Double timeout) {
        this.time = time;
        this.timeout = timeout;
    }
}
