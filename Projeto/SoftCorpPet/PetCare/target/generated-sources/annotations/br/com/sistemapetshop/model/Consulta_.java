package br.com.sistemapetshop.model;

import br.com.sistemapetshop.model.StatusConsulta;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-10-21T04:39:39")
@StaticMetamodel(Consulta.class)
public abstract class Consulta_ { 

    public static volatile SingularAttribute<Consulta, Date> dataMarcada;
    public static volatile SingularAttribute<Consulta, Long> idConsulta;
    public static volatile SingularAttribute<Consulta, StatusConsulta> status;

}