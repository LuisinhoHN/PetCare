package br.com.sistemapetshop.model;

import br.com.sistemapetshop.model.ConsultaGeral;
import br.com.sistemapetshop.model.EspecialidadeFuncionario;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-10-21T04:39:39")
@StaticMetamodel(Funcionario.class)
public class Funcionario_ extends Usuario_ {

    public static volatile SingularAttribute<Funcionario, EspecialidadeFuncionario> especialidadeFuncionario;
    public static volatile ListAttribute<Funcionario, ConsultaGeral> listaConsultaGeral;

}