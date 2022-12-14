package br.com.sistemapetshop.model;

import br.com.sistemapetshop.model.ConsultaMedica;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-10-21T04:39:39")
@StaticMetamodel(Veterinario.class)
public class Veterinario_ extends Usuario_ {

    public static volatile SingularAttribute<Veterinario, String> especialidade;
    public static volatile SingularAttribute<Veterinario, String> crmv;
    public static volatile ListAttribute<Veterinario, ConsultaMedica> ListaConsultaMedica;

}