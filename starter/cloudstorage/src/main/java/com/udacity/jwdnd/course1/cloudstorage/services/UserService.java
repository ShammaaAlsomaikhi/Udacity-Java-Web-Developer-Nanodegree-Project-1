package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashService hashService;
    private final EncryptionService encryptionService;

    public UserService(UserMapper userMapper, HashService hashService, EncryptionService encryptionService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
        this.encryptionService = encryptionService;
    }

    public boolean checkIfUsernameAvailable(String username) {
        if(userMapper.getUserByUserName(username) == null){
            return true;
        }
        return false;
    }

    public int createUser(User user) {
        String salt = encryptionService.generateKey();
        user.setSalt(salt);
        user.setPassword(hashService.getHashedValue(user.getPassword(), salt));

        return userMapper.addUser(user);
    }

    public User getUser(String username) {
        return userMapper.getUserByUserName(username);
    }
}
