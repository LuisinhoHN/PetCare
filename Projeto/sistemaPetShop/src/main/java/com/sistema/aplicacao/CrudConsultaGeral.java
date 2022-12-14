/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.aplicacao;

import com.sistema.model.ConsultaGeral;
import com.sistema.model.EspecialidadeFuncionario;
import com.sistema.model.Funcionario;
import com.sistema.model.Servico;
import com.sistema.model.StatusConsulta;
import java.time.Instant;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Allan Santos, Jonathan Romualdo
 */
public class CrudConsultaGeral {
    /*FUNCIONANDO OK!!!*/
    
    private final static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("sistemapetshopPU");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Long idConsultaGeral;
        ConsultaGeral consultaGeral;

        try {
            //inserir ----------------------------- OK
            idConsultaGeral = inserirConsulta();

            //consultar ----------------------------- OK
            consultaGeral = buscarConsulta(idConsultaGeral);

            if (consultaGeral != null) {

                System.out.println("Funcionario: " + consultaGeral.getFuncionario().getNome());
                System.out.println("Serviço: " + consultaGeral.getServico().getNome());

                consultaGeral.setStatus(StatusConsulta.CONCLUIDA);
                
                //atualizar ----------------------------- OK
                atualizarConsulta(consultaGeral);
            }
            
            //deletar ----------------------------- OK
            deletarConsulta(consultaGeral);

        } finally {
            EMF.close();
        }
   
    }

    public static Long inserirConsulta() {
        EntityManager em = null;
        EntityTransaction et = null;

        ConsultaGeral consultaGeral = preencheConsultaGeral();

        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();

            et.begin();
            em.persist(consultaGeral);
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

        return consultaGeral.getIdConsulta();
    }

    public static void atualizarConsulta(ConsultaGeral consulta) {
        EntityManager em = null;
        EntityTransaction et = null;

        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();

            et.begin();
            em.merge(consulta);
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

    public static void deletarConsulta(ConsultaGeral consulta) {
        EntityManager em = null;
        EntityTransaction et = null;

        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();
            ConsultaGeral consultaGeralRemove = em.merge(consulta);

            et.begin();
            em.remove(consultaGeralRemove);
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

    public static ConsultaGeral buscarConsulta(Long idConsultaGeral) {
        EntityManager em = null;
        ConsultaGeral consultaGeralResultado = null;

        try {
            em = EMF.createEntityManager();

            consultaGeralResultado = em.find(ConsultaGeral.class, idConsultaGeral);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return consultaGeralResultado;
    }

    /*
     * -----------------------------------------------------------
     * | Área destinada a preencher os dados para o CONSULTA GERAL. |
     * -----------------------------------------------------------
     */
    
    public static ConsultaGeral preencheConsultaGeral() {
        ConsultaGeral consultaGeral = new ConsultaGeral();
        Servico servico = preencheServico();
        Funcionario funcionario = preencheFuncionario();

        consultaGeral.setDataMarcada(Date.from(Instant.now()));
        consultaGeral.setServico(servico);
        consultaGeral.setFuncionario(funcionario);
        consultaGeral.setStatus(StatusConsulta.CONSULTADA);

        return consultaGeral;
    }

    public static Servico preencheServico() {
        Servico servico = new Servico();

        servico.setNome("Banho");
        servico.setValor(300.00);

        return servico;
    }

    public static Funcionario preencheFuncionario() {
        Funcionario funcionario = new Funcionario();

        funcionario.setEspecialidadeFuncionario(EspecialidadeFuncionario.TOSADOR);
        funcionario.setNome("João das Nevis");
        funcionario.setEmail("joao@gmail.com");
        funcionario.setLogin("joaonevis7");
        funcionario.setSenha("12345678");

        return funcionario;
    }

}
