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
 * @author Jonathan Romualdo, Allan Santos
 */
@Entity
@Table(name = "tb_cliente")
@DiscriminatorValue(value = "cli")
@PrimaryKeyJoinColumn(name = "id_cliente", referencedColumnName = "id_usuario")
@Access(AccessType.FIELD)
@NamedQueries(
        {
            @NamedQuery(
                    name = "Cliente.PorId",
                    query = "SELECT c FROM Cliente c WHERE c.idUsuario = ?1"
            ),
            @NamedQuery(
                    name = "Cliente.todos",
                    query = "From Cliente c"
            )
        }
)
@NamedNativeQueries(
        {
            @NamedNativeQuery(
                    name = "Cliente.PorLoginSQL",
                    query = "SELECT id_usuario, str_email, str_login, str_nome, str_senha, fk_endereco "
                    + "FROM sistemapet.tb_cliente where str_login like ? ;",
                    resultClass = Cliente.class)
        }
)
public class Cliente extends Usuario implements Serializable {

    public static final String TODOS = "Cliente.todos";

    // Relacionamento Cartao
    @Valid
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Cartao> cartao;

    // Relacionamento Pet
    @Valid
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Pet> listaPet;

    // getters e Setters -----------------------------
    public List<Cartao> getCartao() {
        return cartao;
    }

    public void setCartao(List<Cartao> cartao) {
        this.cartao = cartao;
    }

    public List<Pet> getListaPet() {
        return listaPet;
    }

    public void setListaPet(List<Pet> listaPet) {
        this.listaPet = listaPet;
    }

}
