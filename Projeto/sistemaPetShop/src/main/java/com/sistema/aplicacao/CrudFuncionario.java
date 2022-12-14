/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sistema.aplicacao;

import com.sistema.model.ConsultaGeral;
import com.sistema.model.Endereco;
import com.sistema.model.EspecialidadeFuncionario;
import com.sistema.model.Funcionario;
import com.sistema.model.Servico;
import com.sistema.model.StatusConsulta;
import com.sistema.model.Usuario;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Jonathan Romualdo
 */
public class CrudFuncionario {
    /*FUNCIONANDO OK!!!*/

    private final static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("sistemapetshopPU");
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Long idFuncionario;
        Funcionario funcionario;
        
        try{
            //inserir ------------------------ OK
            idFuncionario = inserirFuncionario();
            
            //consultar ------------------------ OK
            funcionario = consultarFuncionario(idFuncionario);
            
            if(funcionario != null){
                System.out.println(funcionario.getNome());
                System.out.println(funcionario.getLogin());
                System.out.println(funcionario.getEmail());
                System.out.println(funcionario.getEspecialidadeFuncionario());
                
                funcionario.setEmail("novoFuncionario@novo.com");
                funcionario.setLogin("novoFuncionario");
                funcionario.setNome("NovoFuncionario fun");
                funcionario.setEspecialidadeFuncionario(EspecialidadeFuncionario.BANHISTA);
                
                //atualizar ------------------------ OK
                atualizarFuncionario(funcionario);
            }
            
            //deltar ------------------------ OK
            deletarFuncionario(funcionario);
        } finally{
            EMF.close();
        }
    }
    
    public static Long inserirFuncionario(){
        EntityManager em = null;
        EntityTransaction et = null;
        
        Funcionario funcionario = preencherFuncionario();

        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();
            
            et.begin();
            em.persist(funcionario);
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
        
        return funcionario.getIdUsuario();
    }
    
    public static void atualizarFuncionario(Funcionario funcionario){
        EntityManager em = null;
        EntityTransaction et = null;

        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();
            
            et.begin();
            em.merge(funcionario);
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

    public static void deletarFuncionario(Funcionario funcionario){
        EntityManager em = null;
        EntityTransaction et = null;

        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();
            
            Funcionario funcionarioRemove = em.merge(funcionario);
            
            et.begin();
            em.remove(funcionarioRemove);
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
 
    public static Funcionario consultarFuncionario(Long idFuncionario){
        EntityManager em = null;
        
        Funcionario funcionario = null;

        try {
            em = EMF.createEntityManager();
            
            funcionario = em.find(Funcionario.class, idFuncionario);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
        return funcionario;
    }
         
    /*
     * -----------------------------------------------------------
     * | Área destinada a preencher os dados para o FUNCIONARIO. |
     * -----------------------------------------------------------
     */

    private static Funcionario preencherFuncionario() {
        Funcionario funcionario = new Funcionario();
        Endereco endereco = preencheEndereco(funcionario);
        List<ConsultaGeral> listaConsultaGeral = preencheLista(funcionario);
        
        funcionario.setEmail("funcionario@fun.com");
        funcionario.setEndereco(endereco);
        funcionario.setEspecialidadeFuncionario(EspecialidadeFuncionario.TOSADOR);
        funcionario.setListaConsultaGeral(listaConsultaGeral);
        funcionario.setLogin("FuncionarioO");
        funcionario.setNome("Funcionario fun");
        funcionario.setSenha("funcionario123");
        
        return funcionario;
    }
    
    private static Endereco preencheEndereco(Usuario usuario) {

        Endereco endereco = new Endereco();

        endereco.setBairro("Bairro");
        endereco.setCep("12763818");
        endereco.setComplemento("Perto dali");
        endereco.setLogradouro("Avenida");
        endereco.setNumero(222);
        endereco.setUsuario(usuario);

        return endereco;
    }
    
    private static List<ConsultaGeral> preencheLista(Funcionario funcionario){
    
        ConsultaGeral consultaGeral  = preencheConsultaGeral(funcionario);
        
        List<ConsultaGeral> listaConsultaGeral = new ArrayList<>();
        listaConsultaGeral.add(consultaGeral);
        
        return listaConsultaGeral;
    }

    private static ConsultaGeral preencheConsultaGeral(Funcionario funcionario) {
        Servico servico = preencheServico();

        ConsultaGeral consultaGeral = new ConsultaGeral();
        
        consultaGeral.setDataMarcada(Date.from(Instant.now()));
        consultaGeral.setFuncionario(funcionario);
        consultaGeral.setServico(servico);
        consultaGeral.setStatus(StatusConsulta.MARCADA);
        
        return consultaGeral;
    }

    private static Servico preencheServico() {
        Double valor = 200.0;
        
        Servico servico = new Servico();
        servico.setNome("Tosa Avançada");
        servico.setValor(valor);
        
        return servico;
    }
}
