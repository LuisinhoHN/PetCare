package br.com.sistemapetshop.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
 * @author Jonathan Romualdo e Luis Henrique
 */
@Entity
@Table(name = "tb_funcionario")
@DiscriminatorValue(value = "fun")
@PrimaryKeyJoinColumn(name = "id_funcionario", referencedColumnName = "id_usuario")
@Access(AccessType.FIELD)

@NamedQueries(
        {
            @NamedQuery(name = "Funcionario.todos",
                    query = "From Funcionario f")
        }
)
@NamedNativeQueries(
        {
            @NamedNativeQuery(name = "Funcionario.PorNome",
                    query = "select id_funcionario, str_nome from sistemapet.tb_funcionario where str_nome like ? ;",
                    resultClass = Funcionario.class)
        }
)
public class Funcionario extends Usuario implements Serializable {

    public static final String TODOS = "Funcionario.todos";

    @Enumerated(EnumType.STRING)
    @Column(name = "enum_especialidadeFuncionario")
    private EspecialidadeFuncionario especialidadeFuncionario;

    // Relacionamento ConsultaGeral
    @Valid
    @OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
    private List<ConsultaGeral> listaConsultaGeral;

    public List<ConsultaGeral> getListaConsultaGeral() {
        return listaConsultaGeral;
    }

    public void setListaConsultaGeral(List<ConsultaGeral> listaConsultaGeral) {
        this.listaConsultaGeral = listaConsultaGeral;
    }

    // getters e Setters -----------------------------
    public EspecialidadeFuncionario getEspecialidadeFuncionario() {
        return especialidadeFuncionario;
    }

    public void setEspecialidadeFuncionario(EspecialidadeFuncionario especialidadeFuncionario) {
        this.especialidadeFuncionario = especialidadeFuncionario;
    }
}
