package br.com.sistemapetshop.model;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
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
@DiscriminatorColumn(name = "disc_usuario", discriminatorType = DiscriminatorType.STRING, length = 3) // 3 é o tamanho do campo discriminator (disc_usuario)
@Access(AccessType.FIELD)
public abstract class Usuario implements Serializable {
    
    public static final String VETERINARIO = "veterinario";
    public static final String ADMINISTRADOR = "administrador";
    public static final String CLIENTE = "cliente";
    public static final String FUNCIONARIO = "funcionario";
    public static final String USUARIO = "usuario";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    protected Long idUsuario; // O id é herdado pelos filhos

    @NotBlank
    @Pattern(regexp = "\\p{Upper}{1}\\p{Lower}+", message = "Deve conter as iniciais maiúsculas")
    @Column(name = "str_nome", length = 60, nullable = false)
    private String nome;

    @NotNull
    @Size(max = 60)
    @Column(name = "str_email", length = 60, nullable = false, unique = true)
    private String email;

    @NotBlank
    @Size(max = 60)
    @Column(name = "str_login", length = 60, nullable = false, unique = true)
    private String login;

    @NotBlank
    @Size(min = 8, max = 255)
    @Column(name = "str_senha", length = 255, nullable = false)
    private String senha;

    @Size(max = 255)
    @Column(name = "str_sal")
    private String sal;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tb_usuario_grupo", joinColumns = {
        @JoinColumn(name = "id_usuario")},
            inverseJoinColumns = {
                @JoinColumn(name = "id_grupo")})
    private List<Grupo> grupos;

    // Relacionamento Endereco
    @OneToOne(fetch = FetchType.LAZY, optional = false, orphanRemoval = true, // optional false indica que é obrigado colocar endereço para dar persist
            cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_endereco", referencedColumnName = "id_endereco")
    private Endereco endereco;

    public Usuario() {

    }

    @PrePersist
    public void gerarHash() {
        try {
            gerarSal();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            setSenha(sal + senha);
            digest.update(senha.getBytes(Charset.forName("UTF-8")));
            setSenha(Base64.getEncoder().encodeToString(digest.digest()));
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void gerarSal() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        setSal(Base64.getEncoder().encodeToString(randomBytes));
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

    public String getSal() {
        return sal;
    }

    public void setSal(String sal) {
        this.sal = sal;
    }

    public void setGrupo(Grupo grupo) {
        if (this.grupos == null) {
            this.grupos = new ArrayList<>();
        }

        this.grupos.add(grupo);
    }

    public List<Grupo> getGrupos() {
        return this.grupos;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (login != null ? login.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.login == null && other.login != null) || (this.login != null && !this.login.equals(other.login))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "acesso.Usuario[ txtLogin=" + login + " ]";
    }

}
