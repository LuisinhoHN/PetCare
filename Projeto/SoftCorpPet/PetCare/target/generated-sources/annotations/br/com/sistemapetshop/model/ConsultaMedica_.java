package br.com.sistemapetshop.model;

import br.com.sistemapetshop.model.Exame;
import br.com.sistemapetshop.model.Pet;
import br.com.sistemapetshop.model.Veterinario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-10-21T04:39:39")
@StaticMetamodel(ConsultaMedica.class)
public class ConsultaMedica_ extends Consulta_ {

    public static volatile SingularAttribute<ConsultaMedica, Exame> exame;
    public static volatile SingularAttribute<ConsultaMedica, Veterinario> veterinario;
    public static volatile SingularAttribute<ConsultaMedica, String> diagnostico;
    public static volatile SingularAttribute<ConsultaMedica, Pet> pet;

}