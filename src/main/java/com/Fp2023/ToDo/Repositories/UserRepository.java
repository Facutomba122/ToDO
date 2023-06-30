package com.Fp2023.ToDo.Repositories;

import com.Fp2023.ToDo.Entity.MyUser;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<MyUser, UUID> {
    
    public MyUser findByUsername(String username);
}
