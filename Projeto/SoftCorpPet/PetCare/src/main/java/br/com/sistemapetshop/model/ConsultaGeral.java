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
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.Valid;

/**
 *
 * @author allanfreitas
 */

@Entity
@Table(name = "tb_consulta_geral")
@DiscriminatorValue(value = "ger")
@PrimaryKeyJoinColumn(name = "id_consulta_geral", referencedColumnName = "id_consulta")
@Access(AccessType.FIELD)
@NamedQueries(
        {
            @NamedQuery(
                    name = "ConsultaGeral.PorId",
                    query = "SELECT c FROM ConsultaGeral c WHERE c.idConsulta = ?1"
            ),
            @NamedQuery(
                    name = "ConsultaGeral.todos",
                    query = "From ConsultaGeral c"
            )
        }
)
public class ConsultaGeral extends Consulta implements Serializable {

    public static final String TODOS = "ConsultaGeral.todos";
    
    // Relacionamento Servico
    @Valid
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_servico", referencedColumnName = "id_servico")
    private Servico servico;

    // Relacionamento Funcionario
    @Valid
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_funcionario", referencedColumnName = "id_funcionario")
    private Funcionario funcionario;

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    
    
}
