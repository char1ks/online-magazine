package com.example.petProject1.Repository.User;

import com.example.petProject1.Model.User.User;
import com.example.petProject1.Model.User.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UserRole> {
    User findByUsername(String username);
}
