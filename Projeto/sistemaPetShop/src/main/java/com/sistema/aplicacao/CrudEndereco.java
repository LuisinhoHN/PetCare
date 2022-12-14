/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.aplicacao;

import com.sistema.model.Cliente;
import com.sistema.model.Endereco;
import com.sistema.model.Pet;
import com.sistema.model.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Luis Henrique, Jonathan Romualdo
 */
public class CrudEndereco {
    /*FUNCIONANDO OK!!!*/
    
    private final static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("sistemapetshopPU");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Long idEndereco;
        Endereco endereco;

        try {
            //inserir ----------------------------- OK
            idEndereco = inserirEndereco();

            //consultar ----------------------------- OK
            endereco = consultarEndereco(idEndereco);

            if (endereco != null) {

                System.out.println("Bairro: " + endereco.getBairro());
                System.out.println("Cep: " + endereco.getCep());
                System.out.println("Logradouro: " + endereco.getLogradouro());

                endereco.setBairro("AlphaVille");
                endereco.setLogradouro("Bairro");

                //atualizar ----------------------------- OK
                atualizarEndereco(endereco);
            }

            //deletar ----------------------------- OK
            deletarEndereco(endereco);

        } finally {
            EMF.close();
        }

    }

    public static Long inserirEndereco() {
        EntityManager em = null;
        EntityTransaction et = null;

        Endereco endereco = preencherEndereco();

        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();

            et.begin();
            em.persist(endereco);
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
        
        return endereco.getIdEndereco();
    }

    public static void atualizarEndereco(Endereco endereco) {
        EntityManager em = null;
        EntityTransaction et = null;

        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();

            et.begin();
            em.merge(endereco);
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

    public static void deletarEndereco(Endereco endereco) {
        EntityManager em = null;
        EntityTransaction et = null;

        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();

            Endereco removerEndereco = em.merge(endereco);

            et.begin();
            em.remove(removerEndereco);
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

    public static Endereco consultarEndereco(Long idEndereco) {
        EntityManager em = null;
        Endereco enderecoEncontrado = new Endereco();
        try {
            em = EMF.createEntityManager();

            enderecoEncontrado = em.find(Endereco.class, idEndereco);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return enderecoEncontrado;
    }

    public static Endereco preencherEndereco(Usuario usuario) {

        Endereco endereco = new Endereco();

        endereco.setBairro("Alto do Mandu");
        endereco.setCep("126486912");
        endereco.setComplemento("Subindo o alto");
        endereco.setLogradouro("Comunidade");
        endereco.setNumero(456);
        endereco.setUsuario(usuario);

        return endereco;
    }

    /*
     * -----------------------------------------------------------
     * | Área destinada a preencher os dados para o ENDERECO. |
     * -----------------------------------------------------------
     */
    public static Endereco preencherEndereco() {
        Endereco endereco = new Endereco();
        Cliente cliente = preencherCliente(endereco);

        endereco.setBairro("Aquele Bairro");
        endereco.setCep("12345678");
        endereco.setComplemento("Perto daquele Restaurante");
        endereco.setLogradouro("Avenida");
        endereco.setNumero(123);
        endereco.setUsuario(cliente);

        return endereco;
    }

    public static Cliente preencherCliente(Endereco endereco) {
        List<Pet> listaPet = new ArrayList<>();
        Cliente cliente = new Cliente();

        cliente.setListaPet(listaPet);
        cliente.setEmail("cliente@cli.com");
        cliente.setEndereco(endereco);
        cliente.setLogin("Cliente");
        cliente.setNome("Cliente cli");
        cliente.setSenha("cliente123");

        return cliente;
    }
}
