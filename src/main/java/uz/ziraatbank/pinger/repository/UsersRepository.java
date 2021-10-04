package uz.ziraatbank.pinger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ziraatbank.pinger.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
