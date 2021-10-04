package uz.ziraatbank.pinger.entity;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "services")
@Data
@NoArgsConstructor
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "is_active")
    private Boolean active;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "services", fetch = FetchType.EAGER)
    private List<Ports> ports;

    public Services(String serviceName, Boolean active, Ports port) {
        this.serviceName = serviceName;
        this.active = active;
        port.setServices(this);
    }
}
