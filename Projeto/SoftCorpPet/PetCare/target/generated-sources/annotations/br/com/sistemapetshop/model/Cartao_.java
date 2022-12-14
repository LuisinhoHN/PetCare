package br.com.sistemapetshop.model;

import br.com.sistemapetshop.model.Cliente;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-10-21T04:39:39")
@StaticMetamodel(Cartao.class)
public class Cartao_ { 

    public static volatile SingularAttribute<Cartao, Cliente> cliente;
    public static volatile SingularAttribute<Cartao, Long> idCartao;
    public static volatile SingularAttribute<Cartao, String> numero;
    public static volatile SingularAttribute<Cartao, Date> dataValidade;
    public static volatile SingularAttribute<Cartao, String> bandeira;

}