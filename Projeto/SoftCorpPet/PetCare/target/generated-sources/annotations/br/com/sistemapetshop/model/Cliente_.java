package br.com.sistemapetshop.model;

import br.com.sistemapetshop.model.Cartao;
import br.com.sistemapetshop.model.Pet;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-10-21T04:39:39")
@StaticMetamodel(Cliente.class)
public class Cliente_ extends Usuario_ {

    public static volatile ListAttribute<Cliente, Cartao> cartao;
    public static volatile ListAttribute<Cliente, Pet> listaPet;

}