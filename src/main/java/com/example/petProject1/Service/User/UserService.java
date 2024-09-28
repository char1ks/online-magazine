package com.example.petProject1.Service.User;

import com.example.petProject1.Repository.User.UserRepository;
import com.example.petProject1.Security.User_Details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.petProject1.Model.User.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {
    private UserRepository opetaions;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository opetaions, PasswordEncoder passwordEncoder) {
        this.opetaions = opetaions;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        opetaions.save(user);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userFind= Optional.ofNullable(opetaions.findByUsername(username));
        if (userFind.isEmpty()) {
            throw new UsernameNotFoundException("Директор не найден");
        }
        return new User_Details(userFind.get());
    }
}
