package com.Fp2023.ToDo.Controllers;

import com.Fp2023.ToDo.DTO.UserDTO;
import com.Fp2023.ToDo.Entity.MyUser;
import com.Fp2023.ToDo.Services.UserService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO newUser) throws Exception{
        try {
            if (newUser == null){
                return new ResponseEntity<>("El usuario no ha podido registrarse", HttpStatus.BAD_REQUEST);
            }
            Optional<MyUser> userResponse = userService.findByEmail(newUser.getEmail());
            if (userResponse.isPresent()){
                return new ResponseEntity<>("El email ya se encuentra asociado a una cuenta", HttpStatus.CONFLICT);
            }
            userService.saveUser(newUser);
            return new ResponseEntity<>("Usuario registrado correctamente", HttpStatus.ACCEPTED);
        } catch (Exception e){
           throw e; 
        }
    }
    
    @DeleteMapping("/user/delete")
    public ResponseEntity<?> deleteUser(@RequestParam("id") UUID id){
        
        try {
            if (id == null){
                return new ResponseEntity<>("El usuario no se encuentra registrado", HttpStatus.BAD_REQUEST);
            }
            
            Optional<MyUser> userResponse = userService.findById(id);
            if (userResponse.isPresent()){
                userService.deleteUser(id);
                return new ResponseEntity<>("El usuario fue eliminado", HttpStatus.CONTINUE);
            }
            return new ResponseEntity<>("El usuario no fue encontrado", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            throw e;
        }
    }
    
    @GetMapping("/user/showAll")
    public List<MyUser> showAllUser(){
        return userService.findAll();
    }
    
    @PutMapping("/user/update")
    public ResponseEntity<?> updateUser(@RequestBody MyUser user){
        try {
            Optional<MyUser> userResponse = userService.findById(user.getId());
            if (userResponse.isPresent()){
                userService.updateUser(user, userResponse.get());
                return new ResponseEntity<>("Usuario actualizado con exito", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("Usuario no encontrado", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            throw e;
        }
        
    }
}