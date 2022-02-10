package uz.ziraatbank.pinger.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ziraatbank.pinger.config.Status;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DownHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serviceName;
    private String host;
    private Integer port;
    private String time;
    private Status status;

    public DownHistory(String serviceName, String host, Integer port, String time, Status status) {
        this.serviceName = serviceName;
        this.host = host;
        this.port = port;
        this.time = time;
        this.status = status;
    }
}
