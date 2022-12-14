package br.com.sistemapetshop.model;

import br.com.sistemapetshop.model.ConsultaMedica;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-10-21T04:39:39")
@StaticMetamodel(Exame.class)
public class Exame_ { 

    public static volatile SingularAttribute<Exame, String> tipo;
    public static volatile ListAttribute<Exame, ConsultaMedica> listaConsultaMedica;
    public static volatile SingularAttribute<Exame, Double> valor;
    public static volatile SingularAttribute<Exame, String> nome;
    public static volatile SingularAttribute<Exame, Long> idExame;
    public static volatile SingularAttribute<Exame, String> descricao;

}