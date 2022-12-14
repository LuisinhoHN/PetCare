/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapetshop.model;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Jonathan Romauldo
 */
@Entity
@Table(name = "tb_servico")
@Access(AccessType.FIELD)
@NamedQueries(
        {
            @NamedQuery(name = "Servico.PorNome",
                    query = "from Servico s where s.nome like :nome order by s.nome")
            ,
            @NamedQuery(name = "Servico.todos",
                    query = "From Servico s")
        }
)
@NamedNativeQueries(
        {
            @NamedNativeQuery(name = "Servico.PorPreco",
                    query = "select ser.str_nome, ser.dbl_valor from tb_servico ser where ser.dbl_valor like ?")
        }
)
public class Servico implements Serializable {

    public static final String TODOS = "Servico.todos";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servico", nullable = false, unique = true)
    private Long idServico;

    @NotBlank
    @Column(name = "str_nome", nullable = false, unique = false)
    private String nome;

    @NotNull
    @Column(name = "dbl_valor", nullable = false, unique = false)
    private Double valor;

    // Relacionamento Consulta
    @Valid
    @OneToMany(mappedBy = "servico", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    //@JoinColumn (name = "fk_consulta_geral", referencedColumnName = "id_consulta_geral")
    private List<ConsultaGeral> listaConsultaGeral;

    // getters e Setters -----------------------------
    public Long getIdServico() {
        return idServico;
    }

    public void setIdServico(Long idServico) {
        this.idServico = idServico;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public List<ConsultaGeral> getListaConsultaGeral() {
        return listaConsultaGeral;
    }

    public void setListaConsultaGeral(List<ConsultaGeral> listaConsultaGeral) {
        this.listaConsultaGeral = listaConsultaGeral;
    }

}
