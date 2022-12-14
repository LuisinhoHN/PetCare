/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapetshop.bean;

import br.com.sistemapetshop.model.Cartao;
import br.com.sistemapetshop.negocio.ClienteService;
import br.com.sistemapetshop.model.Cliente;
import br.com.sistemapetshop.model.Endereco;
import br.com.sistemapetshop.model.Pet;
import br.com.sistemapetshop.negocio.GrupoService;
import br.com.sistemapetshop.util.WebServiceCep;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import org.omnifaces.util.Messages;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author jonathanpereira
 */
@ManagedBean(name = "clienteManagedBean")
@SessionScoped
public class ClienteBean implements Serializable {

    @EJB
    private ClienteService clienteService;

    @EJB
    private GrupoService grupoService;

    private List<Cliente> listaClientes;
    private List<Cartao> listaCartoes;
    private List<Pet> listaPets;
    private Cartao cartao;
    private Endereco endereco;
    private Cliente cliente;

    private boolean skip;

    @PostConstruct
    public void inicializar() {
        listaCartoes = new ArrayList<>();
        listaPets = new ArrayList<>();

        endereco = new Endereco();
        cliente = new Cliente();
        cartao = new Cartao();

        listar();
    }

    public void inicializarCartao() {
        cartao = new Cartao();
    }

    public void salvar() {

        try {

            cliente.setGrupo(grupoService.getGrupo(new String[]{Cliente.CLIENTE}));

            //Atribuir o endereço ao cliente antesde persistir
            cliente.setEndereco(endereco);
            cliente.setListaPet(listaPets);
            listaCartoes.add(cartao);
            cliente.setCartao(listaCartoes);

            clienteService.salvar(cliente);

            Messages.addGlobalInfo("Salvo com sucesso.");
        } catch (Exception ex) {

            Messages.addGlobalError("Ocorreu um erro inesperado.");
            ex.printStackTrace();
        }
    }

    public void atualizar() {

        try {

            clienteService.atualizar(cliente);

            Messages.addGlobalInfo("Atualizado com sucesso.");
        } catch (Exception ex) {

            Messages.addGlobalError("Ocorreu um erro inesperado.");
            ex.printStackTrace();
        }
    }

//    public void remover() {
//
//        try {
//
//            clienteService.remover(cliente);
//
//            Messages.addGlobalInfo("Cliente removido com sucesso!");
//        } catch (Exception ex) {
//
//            Messages.addGlobalError("Ocorreu um erro inesperado.");
//            ex.printStackTrace();
//        }
//    }
     public void excluir(ActionEvent evento) {
        

        String defaultSuccessMsg = "Cliente removido com sucesso!";
        String defaultErrorMsg = "Ocorreu um erro inesperado.";
        
        //Qual foi o componente clicado? Qual são os atributos? Qual o nome do atributo que eu quero trabalhar?
        cliente = (Cliente) evento.getComponent().getAttributes().get("servicoSelecionado");

        try {
            clienteService.remover(cliente);

            Messages.addGlobalInfo(defaultSuccessMsg);
            //constroi();
            inicializar();
        } catch (Exception erro) {
            Messages.addGlobalError(defaultErrorMsg);
            erro.printStackTrace();
        }
    }


    public void listar() {

        try {

            listaClientes = clienteService.listar();
        } catch (Exception ex) {

            Messages.addGlobalError("Ocorreu um erro inesperado ao carregar a lista.");
            ex.printStackTrace();
        }
    }

    public void editar(ActionEvent evento) {
        cliente = (Cliente) evento.getComponent().getAttributes().get("servicoSelecionado");
    }
    
    public void buscaCep() {

        WebServiceCep webServiceCep = WebServiceCep.searchCep(endereco.getCep());

        if (webServiceCep.wasSuccessful()) {

            endereco.setLogradouro(webServiceCep.getLogradouroFull());
            endereco.setBairro(webServiceCep.getBairro());

            Messages.addGlobalInfo("Cep encontrado!");
        } else {

            Messages.addGlobalError("Cep não encontrado");
        }
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public List<Cartao> getListaCartoes() {
        return listaCartoes;
    }

    public void setListaCartoes(List<Cartao> listaCartoes) {
        this.listaCartoes = listaCartoes;
    }

    public List<Pet> getListaPets() {
        return listaPets;
    }

    public void setListaPets(List<Pet> listaPets) {
        this.listaPets = listaPets;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
