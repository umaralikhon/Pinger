package uz.ziraatbank.pinger.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ziraatbank.pinger.model.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
