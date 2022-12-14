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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Jonathan Romualdo, Luis Henrique
 */
public class CrudPet {
    /*FUNCIONANDO OK!!!*/
    
    private final static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("sistemapetshopPU");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Pet pet;
        Long idPet;

        try {
            //inserir ------------------------ OK
            idPet = inserirPet();
            
            //consultar ------------------------ OK
            pet = consultarPet(idPet);

            if (pet != null) {

                System.out.println(pet.getNome());
                System.out.println(pet.getPedegree());
                System.out.println(pet.getRaca());

                pet.setNome("Dogão");
                pet.setPedegree(Boolean.FALSE);
                pet.setRaca("Vira-lata");
                
                //atualizar ------------------------ OK
                atualizarPet(pet);
            }

            //deletar ------------------------ OK
            deletarPet(pet);

        } finally {
            EMF.close();
        }

    }

    public static Long inserirPet() {
        EntityManager em = null;
        EntityTransaction et = null;

        Pet pet = preencherPet();

        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();

            et.begin();
            em.persist(pet);
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

        return pet.getIdPet();
    }

    public static void atualizarPet(Pet pet) {
        EntityManager em = null;
        EntityTransaction et = null;

        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();

            et.begin();
            em.merge(pet);
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

    public static void deletarPet(Pet pet) {
        EntityManager em = null;
        EntityTransaction et = null;

        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();
            
            Pet petRemove = em.merge(pet);

            et.begin();
            em.remove(petRemove);
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

    public static Pet consultarPet(Long idPet) {
        EntityManager em = null;

        Pet pet = null;

        try {
            em = EMF.createEntityManager();

            pet = em.find(Pet.class, idPet);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return pet;
    }

    /*
     * -----------------------------------------------------------
     * | Área destinada a preencher os dados para o PET. |
     * -----------------------------------------------------------
     */
    public static Pet preencherPet() {
        Float peso = 24f;
        Cliente cliente = preencheCliente();
        Pet pet = new Pet();

        pet.setCliente(cliente);
        pet.setNome("Tótó");
        pet.setPedegree(Boolean.TRUE);
        pet.setPeso(peso);
        pet.setRaca("Labrador");

        return pet;
    }

    private static Cliente preencheCliente() {

        Cliente cliente = new Cliente();
        Endereco endereco = preencherEndereco(cliente);

        cliente.setEmail("cliente2@cli.com");
        cliente.setEndereco(endereco);
        cliente.setLogin("cliente_gastador2");
        cliente.setNome("Cliente cli");
        cliente.setSenha("cliente123");

        return cliente;
    }

    private static Endereco preencherEndereco(Usuario usuario) {

        Endereco endereco = new Endereco();

        endereco.setBairro("Bairro");
        endereco.setCep("12763818");
        endereco.setComplemento("Perto dali");
        endereco.setLogradouro("Avenida");
        endereco.setNumero(222);
        endereco.setUsuario(usuario);

        return endereco;
    }
}
