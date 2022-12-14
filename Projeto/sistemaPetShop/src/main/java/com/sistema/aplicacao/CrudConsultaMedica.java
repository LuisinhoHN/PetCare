/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.aplicacao;

import com.sistema.model.Cliente;
import com.sistema.model.ConsultaMedica;
import com.sistema.model.Endereco;
import com.sistema.model.Exame;
import com.sistema.model.Pet;
import com.sistema.model.StatusConsulta;
import com.sistema.model.Usuario;
import com.sistema.model.Veterinario;
import java.time.Instant;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Jonathan Romualdo, Luis Henrique
 */
public class CrudConsultaMedica {
    /*FUNCIONANDO OK!!!*/

    private final static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("sistemapetshopPU");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Long idConsultaMedica;
        ConsultaMedica consulta;
        
        try{
            //inserir ----------------------------- OK
            idConsultaMedica = inserirConsultaMedica();
            
            //buscar ----------------------------- OK
            consulta = buscarConsultaMedica(idConsultaMedica);
            
            if(consulta != null){
                System.out.println(consulta.getExame());
                System.out.println(consulta.getDiagnostico());
                System.out.println(consulta.getPet().getNome());
                System.out.println(consulta.getVeterinario().getNome());
                
                consulta.setDiagnostico("Muita Água e muita comida");
                consulta.setStatus(StatusConsulta.CONCLUIDA);
                consulta.setDiagnostico("o fim é a morte");
                
                //atualizar ----------------------------- OK
                atualizarConsultaMedica(consulta);
            }
            
            //deletar ----------------------------- OK
            deletarConsultaMedica(consulta);
            
        }finally{
            EMF.close();
        }
        
    }
    
    public static Long inserirConsultaMedica(){
        EntityManager em = null;
        EntityTransaction et = null;
        
        ConsultaMedica consultaMedica = preencheConsultaMedica();

        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();
            
            et.begin();
            em.persist(consultaMedica);
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
        
        return consultaMedica.getIdConsulta();
    }
    
    public static void atualizarConsultaMedica(ConsultaMedica consultaMedica){
        EntityManager em = null;
        EntityTransaction et = null;

        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();
            
            et.begin();
            em.merge(consultaMedica);
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

    public static void deletarConsultaMedica(ConsultaMedica consultaMedica){
        EntityManager em = null;
        EntityTransaction et = null;

        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();
            
            ConsultaMedica consultaMedicaRemove = em.merge(consultaMedica);
            
            et.begin();
            em.remove(consultaMedicaRemove);
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
 
    public static ConsultaMedica buscarConsultaMedica(Long idConsultaMedica){
        EntityManager em = null;
        
        ConsultaMedica consultaMedica = null;

        try {
            em = EMF.createEntityManager();

            consultaMedica = em.find(ConsultaMedica.class, idConsultaMedica);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
        return consultaMedica;
    }
    
    /*
     * -----------------------------------------------------------
     * | Área destinada a preencher os dados para o CONSULTA MEDICA. |
     * -----------------------------------------------------------
     */

    private static ConsultaMedica preencheConsultaMedica() {  
        ConsultaMedica consultaMedica = new ConsultaMedica();
        Veterinario veterinario = preencherVeterinario();
        Exame exame = preencheExame();
        Pet pet = preenchePet();
        
        consultaMedica.setDataMarcada(Date.from(Instant.now()));
        consultaMedica.setDiagnostico("Muita água");
        consultaMedica.setExame(exame);
        consultaMedica.setPet(pet);
        consultaMedica.setStatus(StatusConsulta.MARCADA);
        consultaMedica.setVeterinario(veterinario);
        
        return consultaMedica;
    }
    
    private static Exame preencheExame(){
    
        Exame exame = new Exame();
        
        exame.setDescricao("Exame de rotina");
        exame.setNome("Rotina");
        exame.setTipo("Caro");
        exame.setValor(300.0);
        
        return exame;
    }
    
    private static Pet preenchePet(){
        
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
    
    private static Cliente preencheCliente(){
               
        Cliente cliente = new Cliente();
        Endereco endereco = preencherEndereco(cliente);
 
        cliente.setEmail("cliente3@cli.com");
        cliente.setEndereco(endereco);
        cliente.setLogin("cliente_gastador3");
        cliente.setNome("Cliente cli");
        cliente.setSenha("cliente123");
        
        return cliente;
    }
    
    private static Endereco preencherEndereco(Usuario usuario){
        
        Endereco endereco = new Endereco();
        
        endereco.setBairro("Bairro");
        endereco.setCep("12763818");
        endereco.setComplemento("Perto dali");
        endereco.setLogradouro("Avenida");
        endereco.setNumero(222);
        endereco.setUsuario(usuario);
        
        return endereco;
    }
    
    private static Veterinario preencherVeterinario() {
        
        Veterinario veterinario = new Veterinario();
        Endereco endereco = preencherEndereco(veterinario);
        
        Long id = 1l;
        
        veterinario.setCrmv("crmvPadraoTeste123");
        veterinario.setEmail("veterinario@vet.com");
        veterinario.setEndereco(endereco);
        veterinario.setEspecialidade("cirurgião");
        veterinario.setLogin("melhorVeterinario123");
        veterinario.setNome("Veterinário Severino");
        veterinario.setSenha("veterinario1234");
        //veterinario.setIdUsuario(id);
        
        return veterinario;
    }
}
