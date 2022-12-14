/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.aplicacao;

import com.sistema.model.ConsultaMedica;
import com.sistema.model.Exame;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Henrique, Jonathan Romualdo
 */
public class CrudExame {
    /*FUNCIONANDO OK!!!*/

    private final static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("sistemapetshopPU");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Long idExame;
        Exame exame;

        try {
            // Inserir ----------------------- OK
            idExame = inserirExame();

            //Consultar --------------------- OK
            exame = consultarExame(idExame);

            if (exame != null) {
                System.out.println("Nome: " + exame.getNome());
                System.out.println("Tipo: " + exame.getTipo());
                System.out.println("Descrição: " + exame.getDescricao());
                System.out.println("Valor: " + exame.getValor());

//        
                System.out.println("atualizar");
                exame.setValor(600.00);

                //Atualizar ----------------------------- OK
                atualizarExame(exame);
            }

            // Deletar ------------------------------ NOK
            deletarExame(exame);

        } finally {
            EMF.close();
        }

    }

    public static Long inserirExame() {
        EntityManager em = null;
        EntityTransaction et = null;

        Exame exame = preencherExame();

        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();

            et.begin();
            em.persist(exame);
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

        return exame.getIdExame();
    }

    public static void atualizarExame(Exame exame) {
        EntityManager em = null;
        EntityTransaction et = null;

        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();

            et.begin();
            em.merge(exame);
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

    public static void deletarExame(Exame exame) {
        EntityManager em = null;
        EntityTransaction et = null;

        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();
            Exame exameRemover = em.merge(exame);

            et.begin();
            em.remove(exameRemover);
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

    public static Exame consultarExame(Long idExame) {
        EntityManager em = null;
        Exame exameResultado = null;
        try {
            em = EMF.createEntityManager();

            exameResultado = em.find(Exame.class, idExame);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return exameResultado;
    }

    /*
     * -----------------------------------------------------------
     * | Área destinada a preencher os dados para o EXAME. |
     * -----------------------------------------------------------
     */
    public static Exame preencherExame() {
        List<ConsultaMedica> listaConsultaMedica = new ArrayList<>();

        Exame exame = new Exame();

        exame.setDescricao("Ultrasonografia");
        exame.setListaConsultaMedica(listaConsultaMedica);
        exame.setNome("teste");
        exame.setTipo("radiografia em geral");
        exame.setValor(200.00);

        return exame;
    }

}
