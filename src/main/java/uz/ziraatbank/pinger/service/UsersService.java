package uz.ziraatbank.pinger.service;

import uz.ziraatbank.pinger.entity.Users;

import java.util.List;

public interface UsersService {
    List<Users> getAllUsers();
    void save(Users user);
}
