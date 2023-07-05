package com.Fp2023.ToDo.Repositories;

import com.Fp2023.ToDo.Entity.MyUser;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<MyUser, UUID> {
    
    public MyUser findByUsername(String username);
    
    public Optional<MyUser> findByEmail(@Param("email") String email);
}
