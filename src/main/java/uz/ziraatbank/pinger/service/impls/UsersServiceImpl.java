package uz.ziraatbank.pinger.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.ziraatbank.pinger.entity.Users;
import uz.ziraatbank.pinger.repository.UsersRepository;
import uz.ziraatbank.pinger.service.UsersService;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
    private UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    @Override
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public void save(Users user){
        usersRepository.save(user);
    }
}
