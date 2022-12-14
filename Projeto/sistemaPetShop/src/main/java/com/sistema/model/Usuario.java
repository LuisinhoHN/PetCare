package com.sistema.model;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;

/**
 *
 * @author Jonathan Romualdo, Luis Henrique, allanfreitas
 * 
 */
@Entity
@Table(name = "tb_usuario")
@Inheritance(strategy = InheritanceType.JOINED) 
@DiscriminatorColumn(name = "disc_usuario", discriminatorType = DiscriminatorType.STRING, length= 3) // 3 é o tamanho do campo discriminator (disc_usuario)
@Access(AccessType.FIELD)
public abstract class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    protected Long idUsuario; // O id é herdado pelos filhos
    
    @NotBlank
    @Pattern(regexp = "\\p{Upper}{1}\\p{Lower}+", message = "Deve conter as iniciais maiúsculas")
    @Column(name = "str_nome", length = 60, nullable = false)
    private String nome;
    
    @NotNull
    @Email
    @Size(max=60)
    @Column(name = "str_email", length = 60, nullable = false, unique = true)
    private String email;
    
    @NotBlank
    @Size(max=60)
    @Column(name = "str_login", length = 60, nullable = false, unique = true)
    private String login;
    
    @NotBlank
    @Size(min=8, max=16)
    @Pattern(regexp="(?=.*\\p{Digit}).{8,16}")
    @Column(name = "str_senha", length = 16, nullable = false)
    private String senha;

    // Relacionamento Endereco
    @OneToOne(fetch = FetchType.LAZY, optional = false, orphanRemoval = true, // optional false indica que é obrigado colocar endereço para dar persist
            cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_endereco", referencedColumnName = "id_endereco")
    private Endereco endereco;

    public Usuario() {
        
    }

    // getters e Setters -----------------------------
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

}
