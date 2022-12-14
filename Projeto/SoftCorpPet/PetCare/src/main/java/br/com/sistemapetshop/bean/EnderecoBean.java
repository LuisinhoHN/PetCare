/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapetshop.bean;

import br.com.sistemapetshop.model.Endereco;
import br.com.sistemapetshop.negocio.EnderecoService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author jonathanpereira
 */
@ManagedBean(name = "EnderecoManagedBean")
@SessionScoped
public class EnderecoBean {

    @EJB
    private EnderecoService enderecoService;

    private Endereco endereco;

    @PostConstruct
    public void constroi() {
        endereco = new Endereco();
    }

    public List<Endereco> listarEnderecos() {
        return enderecoService.listar();
    }

    public void adicionar() {
        enderecoService.salvar(endereco);
    }

    public Endereco getEndereco() {
        return endereco;
    }
}
