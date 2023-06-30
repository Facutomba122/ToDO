package com.Fp2023.ToDo.Services;

import com.Fp2023.ToDo.DTO.UserDTO;
import com.Fp2023.ToDo.Entity.MyUser;
import com.Fp2023.ToDo.Entity.Roles;
import com.Fp2023.ToDo.Repositories.RolesRepository;
import com.Fp2023.ToDo.Repositories.UserRepository;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository rolRepository;
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    };
    
    
    public MyUser saveUser(UserDTO userDTO) throws Exception{
        try {
            if (userRepository.findByUsername(userDTO.getUsername()) == null){
                MyUser auxUser = new MyUser(
                        userDTO.getUsername(),
                        bCryptPasswordEncoder().encode(userDTO.getPassword()),
                        userDTO.getEmail(),
                        
                        ///ESTE ROL SE CREA AUNQUE YA EXISTA DICHO ROL PORQUE NO BUSCA PRIMERO
                        //IMPLEMENTAR METODO QUE BUSQUE EL ROL SI EXISTE Y LO CREE SINO.
                        Arrays.asList(checkRoles()));
                return userRepository.save(auxUser);
            } else {
                throw new Exception("El nombre de usuario ya existe.");
            }
        } catch (Exception e){
            throw new Exception("No se pudo crear el nuevo usuario. Intente nuevamente más tarde");
        }
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        
        Optional<MyUser> userResponse = Optional.ofNullable(userRepository.findByUsername(username));
        if (userResponse.isPresent()){
            MyUser user = userResponse.get();
            return new User(user.getUsername(), user.getPassword(), mapAuthoritiesToRoles(user.getRol()));
        }
        throw new UsernameNotFoundException("Usuario o contraseña invalidos");
    }
    
    public Roles checkRoles() throws Exception{
        Optional<List<Roles>> responseRoles = Optional.ofNullable(rolRepository.findAll());
        
        if (responseRoles.isPresent()){
            List<Roles> allRoles = responseRoles.get();
            if (allRoles.isEmpty()){
                return new Roles("ADMIN");
            }
            return new Roles("USER");
        }
        throw new Exception("Error in register");
    }
    
    
    
    private Collection<? extends GrantedAuthority> mapAuthoritiesToRoles(Collection<Roles> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
