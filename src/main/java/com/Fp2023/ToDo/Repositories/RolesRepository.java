package com.Fp2023.ToDo.Repositories;

import com.Fp2023.ToDo.Entity.Roles;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RolesRepository extends JpaRepository<Roles, UUID>{
    
}
