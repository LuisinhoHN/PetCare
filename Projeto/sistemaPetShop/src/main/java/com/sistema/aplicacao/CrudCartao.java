/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.aplicacao;

import com.sistema.model.Cartao;
import java.util.Calendar;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Jonathan Romualdo
 */
public class CrudCartao {
    
    /*FUNCIONANDO OK!!!*/

    private final static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("sistemapetshopPU");

    public static void main(String[] args) {

        Long idCartao;
        Cartao cartao;

        try {
            // Inserir ------------------------------------ OK
            idCartao = inserirCartao();

            // Consultar ------------------------------------ OK
            cartao = consultarCartao(idCartao);

            if (cartao != null) {

                System.out.println("Bandeira: " + cartao.getBandeira());
                System.out.println("Numero: " + cartao.getNumero());
                System.out.println("Validade: " + cartao.getDataValidade());

                cartao.setBandeira("Master Card");

                // Atualizar ------------------------------- OK
                atualizarCartao(cartao);
            }

            // Deletar ------------------------------------ NOK
            deletarCartao(cartao);
            
        } finally {
            EMF.close();
        }

    }

    public static Long inserirCartao() {
        EntityManager em = null;
        EntityTransaction et = null;

        Cartao cartao = preencheCartao();

        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();

            et.begin();
            em.persist(cartao);
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

        return cartao.getIdCartao();
    }

    public static void atualizarCartao(Cartao cartao) {
        EntityManager em = null;
        EntityTransaction et = null;

        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();

            et.begin();
            em.merge(cartao);
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

    public static void deletarCartao(Cartao cartao) {
        EntityManager em = null;
        EntityTransaction et = null;

        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();

            Cartao cartaoRemove = em.merge(cartao);

            et.begin();
            em.remove(cartaoRemove);
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

    public static Cartao consultarCartao(Long idCartao) {
        EntityManager em = null;

        Cartao cartaoResultado = null;

        try {
            em = EMF.createEntityManager();

            cartaoResultado = em.find(Cartao.class, idCartao);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return cartaoResultado;
    }

    /*
     * -----------------------------------------------------------
     * | Área destinada a preencher os dados para o CARTAO. |
     * -----------------------------------------------------------
     */
    
    public static Cartao preencheCartao() {
        Calendar calendario = Calendar.getInstance();
        calendario.set(2018, 7, 17);

        Cartao cartao = new Cartao("Visa", "4929293458709012", calendario.getTime());

        return cartao;
    }

}
