package com.sistema.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Jonathan Romualdo, allanfreitas
 */
@Entity
@Table(name = "tb_cartao")
@Access(AccessType.FIELD)
@NamedQueries(
        {
            @NamedQuery(
                    name = "Cartao.PorId",
                    query = "SELECT c FROM Cartao c WHERE c.idCartao = ?1"
            )
        }
)
@NamedNativeQueries(
        {
            @NamedNativeQuery(
                name = "Cartao.PorBandeiraSQL",
                query = "SELECT id_cartao, str_bandeira, date_dataValidade, str_numero "
                        + "FROM sistemapet.tb_cartao where str_bandeira like ? ;",
                resultClass = Cartao.class),
        
            @NamedNativeQuery(
                name = "Cartao.PorIdSQL",
                query = "SELECT id_cartao, str_bandeira, date_dataValidade, str_numero, fk_cliente "
                        + "FROM sistemapet.tb_cartao WHERE str_bandeira id_cartao = ? ;",
                resultClass = Cartao.class),
        }
)
public class Cartao implements Serializable {

    @Id
    @Column(name = "id_cartao", unique = true, nullable = false)
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long idCartao;

    @NotBlank
    @NotNull
    @ValidaBandeira
    @Column(name = "str_bandeira")
    private String bandeira;

    @NotBlank
    @CreditCardNumber
    @Column(name = "str_numero")
    private String numero;

    @NotNull
    @Future
    @Column(name = "date_dataValidade")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataValidade;
    
    // Relacionamento Cliente
    @Valid
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, optional = true)
    @JoinColumn (name = "fk_cliente", referencedColumnName = "id_cliente")
    private Cliente cliente;

    public Cartao() {
    }

    public Cartao(String bandeira, String numero, Date dataValidade) {
        this.bandeira = bandeira;
        this.numero = numero;
        this.dataValidade = dataValidade;
    }  

    @PrePersist
    public void validar() {
        Validation.buildDefaultValidatorFactory().getValidator().validate(this, Cartao.class);
    }
    
    // getters e Setters -----------------------------
    public Long getIdCartao() {
        return idCartao;
    }

    public void setIdCartao(Long idCartao) {
        this.idCartao = idCartao;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
