package com.example.school.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Mapeado como teacher_name

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    // Relacionamento inverso é ignorado aqui para manter a Entidade limpa
    // e evitar o loop de serialização. O mapeamento está em Course.java

    // Construtor Vazio
    public Teacher() {}

    // Construtor com Campos
    public Teacher(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}