package com.Fp2023.ToDo.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class MyUser {

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    private String username;
    private String password;
    @Column(nullable = false)
    private String email;
    private String tasks;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id")
    )
    private Collection<Roles> rol;

    public MyUser() {
    }

    public MyUser(String username, String password, String email, Collection<Roles> rol) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.rol = rol;
    }

    public MyUser(String username, String password, String email, Collection<Roles> rol, String tasks) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.rol = rol;
        this.tasks = tasks;
    }

    public String getTasks() {
        return tasks;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }

    public Collection<Roles> getRol() {
        return rol;
    }

    public void setRol(Collection<Roles> rol) {
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

}
