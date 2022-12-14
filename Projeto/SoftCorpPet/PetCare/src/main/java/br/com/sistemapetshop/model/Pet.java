/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapetshop.model;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Jonathan Romualdo
 */
@Entity
@Table(name = "tb_pet")
@NamedQueries(
        {
            @NamedQuery(name = "Pet.PorNome", 
                query = "from Pet p where p.nome like :nome order by p.nome"
            ),
            @NamedQuery(name = "Pet.todos",
                query = "From Pet p"
            ),
            @NamedQuery(name = "Pet.porLoginUsuario",
                query = "FROM Pet p WHERE p.cliente = (SELECT cliente FROM Cliente c WHERE c.login like ?)"
            )
            
        }
)
@NamedNativeQueries(
    {
        @NamedNativeQuery(name = "Pet.PorRaca",
        query = "select * from tb_pet pet where pet.str_raca like ?")
    }
)
public class Pet implements Serializable {
    
    public static final String POR_NOME = "Pet.PorNome";
    public static final String TODOS = "Pet.todos";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pet")
    private Long idPet;

    @NotBlank
    @Size(max=60)
    @Column(name = "str_nome", nullable = true, length = 60)
    private String nome;

    @NotBlank
    @Column(name = "flt_peso", nullable = false)
    private Double peso;

    @Size(max=60)
    @Column(name = "str_raca", nullable = false, length = 60)
    private String raca;

    @NotBlank
    @Column(name = "boo_pedegree", nullable = false)
    private Boolean pedegree;

    // Relacionamento Cliente
    @Valid
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_cliente", referencedColumnName = "id_cliente")
    private Cliente cliente;

    // Relacionamento ConsultaMedica
    @Valid
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ConsultaMedica> ListaConsultaMedica;

    // getters e Setters -----------------------------
    public Long getIdPet() {
        return idPet;
    }

    public void setIdPet(Long idPet) {
        this.idPet = idPet;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public Boolean getPedegree() {
        return pedegree;
    }

    public void setPedegree(Boolean pedegree) {
        this.pedegree = pedegree;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ConsultaMedica> getListaConsultaMedica() {
        return ListaConsultaMedica;
    }

    public void setListaConsultaMedica(List<ConsultaMedica> ListaConsultaMedica) {
        this.ListaConsultaMedica = ListaConsultaMedica;
    }

}
