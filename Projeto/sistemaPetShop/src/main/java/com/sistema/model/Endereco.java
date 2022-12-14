package com.sistema.model;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Jonathan Romualdo, allanfreitas 
 */
@Entity
@Table(name = "tb_endereco")
@Access(AccessType.FIELD)
@NamedNativeQueries(
        {
            @NamedNativeQuery(name = "Endereco.PorLogradouro",
            query = "select id_endereco, str_logradouro, int_numero, str_cep from sistemapet.tb_endereco where str_logradouro like ? ;",
            resultClass = Endereco.class)
        }
)
public class Endereco implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco", nullable = false, unique = false)
    private Long idEndereco;

    @NotBlank
    @Size(max=60)
    @Column(name = "str_logradouro", nullable = false, length = 60)
    private String logradouro;

    @NotBlank
    @Column(name = "int_numero", nullable = true)
    private Integer numero;

    @NotBlank
    @Size(max=60)
    @Column(name = "str_complemento", length = 60, nullable = true)
    private String complemento;

    @NotBlank
    @Size(max=9)
    // falta colocar pattern de cep
    @Column(name = "str_cep", length = 9, nullable = false)
    private String cep;

    @NotBlank
    @Size(max=60)
    @Column(name = "str_bairro", length = 60, nullable = false)
    private String bairro;

    // Relacionamento Usuario
    @OneToOne(mappedBy = "endereco", optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Usuario usuario;

    public Endereco(){
        
    }
    public Endereco(String logradouro, Integer numero, String complemento, String cep, String bairro, Usuario usuario) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
        this.bairro = bairro;
        this.usuario = usuario;
    }
    
    // getters e Setters -----------------------------
    public Long getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(Long idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
