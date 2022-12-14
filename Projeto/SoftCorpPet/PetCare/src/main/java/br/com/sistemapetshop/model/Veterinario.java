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
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.Valid;

/**
 *
 * @author Jonathan Romualdo
 */
@Entity
@Table(name = "tb_Veterinario")
@DiscriminatorValue(value = "vet")
@PrimaryKeyJoinColumn(name = "id_veterinario", referencedColumnName = "id_usuario")
@NamedQueries(
        {
            @NamedQuery(name = "Veterinario.PorNome",
                query = "from Veterinario v where v.nome like :nome order by v.nome")
            ,
            @NamedQuery(name = "Veterinario.todos",
                query = "From Veterinario v")
        }
)
@NamedNativeQueries(
        {
            @NamedNativeQuery(name = "Veterinario.PorEspecialidade",
                    query = "select id_veterinario, str_crmv, str_especialidade from tb_veterinario where str_especialidade like ? order by str_crmv;")
        }
)
public class Veterinario extends Usuario implements Serializable {

    public static final String TODOS = "Veterinario.todos";
    
    @Column(name = "str_crmv", nullable = false, length = 60, unique = true)
    private String crmv;

    //@Enumerated(EnumType.STRING)
    @Column(name = "str_especialidade", nullable = false, length = 60)
    private String especialidade;

    // Relacionamento ConsultaMedica
    @Valid
    @OneToMany(mappedBy = "veterinario", fetch = FetchType.LAZY, orphanRemoval = false, cascade = CascadeType.ALL)
    private List<ConsultaMedica> ListaConsultaMedica;

    // getters e Setters -----------------------------
    public String getCrmv() {
        return crmv;
    }

    public void setCrmv(String crmv) {
        this.crmv = crmv;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public List<ConsultaMedica> getListaConsultaMedica() {
        return ListaConsultaMedica;
    }

    public void setListaConsultaMedica(List<ConsultaMedica> ListaConsultaMedica) {
        this.ListaConsultaMedica = ListaConsultaMedica;
    }

}
