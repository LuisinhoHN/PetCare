/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.aplicacao;

import com.sistema.model.Cartao;
import com.sistema.model.Cliente;
import com.sistema.model.Endereco;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Jonathan Romualdo
 */
public class CrudCliente {
    /*FUNCIONANDO OK!!!*/

    private final static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("sistemapetshopPU");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Long idCliente;
        Cliente cliente;

        try {
            //inserir ----------------------------- OK
            idCliente = inserirCliente();

            //consultar ----------------------------- OK
            cliente = consultarCliente(idCliente);

            if (cliente != null) {

                System.out.println("Nome: " + cliente.getNome());
                System.out.println("Email: " + cliente.getEmail());

                cliente.setNome("Spock");
                cliente.setEmail("spock@capitaoEnterprise.com");

                //atualizar ----------------------------- OK
                atualizarCliente(cliente);
            }
            
            //deletar ----------------------------- OK
            deletarCliente(cliente);

        } finally {
            EMF.close();
        }

    }

    public static Long inserirCliente() {
        /* :) */
        EntityManager em = null;
        EntityTransaction et = null;

        Cliente cliente = preencheCliente();

        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();

            et.begin();
            em.persist(cliente);
            et.commit();
        } catch (Exception ex) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return cliente.getIdUsuario();
    }

    public static void atualizarCliente(Cliente cliente) {
        EntityManager em = null;
        EntityTransaction et = null;

        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();

            et.begin();
            em.merge(cliente);
            et.commit();
        } catch (Exception ex) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public static void deletarCliente(Cliente cliente) {
        EntityManager em = null;
        EntityTransaction et = null;

        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();

            Cliente clienteRemove = em.merge(cliente);

            et.begin();
            em.remove(clienteRemove);
            et.commit();
        } catch (Exception ex) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

    public static Cliente consultarCliente(Long idCliente) {
        EntityManager em = null;

        Cliente clienteResultado = null;
        try {
            em = EMF.createEntityManager();

            clienteResultado = em.find(Cliente.class, idCliente);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return clienteResultado;
    }

    /*
     * -----------------------------------------------------------
     * | Área destinada a preencher os dados para o CLIENTE. |
     * -----------------------------------------------------------
     */
    public static Cliente preencheCliente() {
        Cliente cliente = new Cliente();
        Endereco endereco = preencherEndereco(cliente);
        Cartao cartao = preencheCartao();

        List<Cartao> listaCartao = new ArrayList<>();
        listaCartao.add(cartao);

        cliente.setNome("James T. Kirk");
        cliente.setCartao(listaCartao);
        cliente.setEmail("kirk@capitao.com");
        cliente.setLogin("kirkCapitao");
        cliente.setSenha("melhorCapitao");
        cliente.setEndereco(endereco);

        return cliente;
    }

    public static Endereco preencherEndereco(Cliente cliente) {
        Endereco endereco = new Endereco();

        endereco.setBairro("Várzea");
        endereco.setCep("400-400");
        endereco.setComplemento("próximo à Evil Corp");
        endereco.setLogradouro("Rua A");
        endereco.setNumero(404);
        endereco.setUsuario(cliente);

        return endereco;
    }

    public static Cartao preencheCartao() {
        Calendar calendario = Calendar.getInstance();
        calendario.set(2018, 7, 17);

        Cartao cartao = new Cartao("Visa", "1999-2002-2003", calendario.getTime());

        return cartao;
    }

}
