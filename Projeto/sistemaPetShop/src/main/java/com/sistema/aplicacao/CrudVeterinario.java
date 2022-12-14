/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.aplicacao;

import com.sistema.model.Cliente;
import com.sistema.model.Veterinario;
import com.sistema.model.ConsultaMedica;
import com.sistema.model.Endereco;
import com.sistema.model.Exame;
import com.sistema.model.Pet;
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
 * @author Jonathan Romualdo, Luis Henrique
 */
public class CrudVeterinario {
    /*FUNCIONANDO OK!!!*/

    private final static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("sistemapetshopPU");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Long idVeternario;
        Veterinario veterinario;
        
        try {

            //inserir ------------------------ OK
            idVeternario = inserirVeterinario();
            
            //consultar ------------------------ OK
            veterinario = consultarVeterinario(idVeternario);
            
            if(veterinario != null){
                System.out.println(veterinario.getNome());
                System.out.println(veterinario.getCrmv());
                System.out.println(veterinario.getEmail());
                System.out.println(veterinario.getEspecialidade());
                
                veterinario.setNome("Diferente");
                veterinario.setEmail("diferente@dif.com");
            
                //atualizar ------------------------ OK
                atualizarVeterinario(veterinario);
            }
    
            //deletar ------------------------ OK
            deletarVeterinario(veterinario);
            
        } finally {
            EMF.close();
        }
    }

    public static Long inserirVeterinario() {
        EntityManager em = null;
        EntityTransaction et = null;

        Veterinario veterinario = preencherVeterinario();
        
        try{
            em = EMF.createEntityManager();
            et = em.getTransaction();
            
            et.begin();
            em.persist(veterinario);
            et.commit();
        } catch (Exception ex) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
            System.out.println("Ocorreu uma exception: " + ex.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return veterinario.getIdUsuario();
    }

    public static void atualizarVeterinario(Veterinario veterinario) {
        EntityManager em = null;
        EntityTransaction et = null;
           
        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();

            et.begin();
            em.merge(veterinario);
            et.commit();
            System.out.println("Dados Atualizados com sucesso!");
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

    public static void deletarVeterinario(Veterinario veterinario) {
        EntityManager em = null;
        EntityTransaction et = null;
           
        try {
            em = EMF.createEntityManager();
            et = em.getTransaction();
            
            Veterinario veterinarioRemove = em.merge(veterinario);

            et.begin();
            em.remove(veterinarioRemove);
            et.commit();
            System.out.println("Veterinario excluido!");
        } catch (Exception ex) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
            System.out.println("Ocorreu um erro ao deletar o dado. Error: " + ex.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

    public static Veterinario consultarVeterinario(Long idVeterinario) {
        EntityManager em = null;

        Veterinario veterinarioEncontrado = null;

        try {
            em = EMF.createEntityManager();

            veterinarioEncontrado = em.find(Veterinario.class, idVeterinario);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
        return veterinarioEncontrado;
    }
    
    /*
     * -----------------------------------------------------------
     * | Área destinada a preencher os dados para o veterinario. |
     * -----------------------------------------------------------
     */

    private static Veterinario preencherVeterinario() {
        
        Veterinario veterinario = new Veterinario();
        Endereco endereco = preencherEndereco(veterinario);
        List<ConsultaMedica> listaConsultaMedica = preencheConsulta(veterinario);
        
        Long id = 1l;
        
        veterinario.setCrmv("crmvPadraoTeste123");
        veterinario.setEmail("veterinario@vet.com");
        veterinario.setEndereco(endereco);
        veterinario.setEspecialidade("cirurgião");
        veterinario.setListaConsultaMedica(listaConsultaMedica);
        veterinario.setLogin("melhorVeterinario123");
        veterinario.setNome("Veterinário Severino");
        veterinario.setSenha("veterinario1234");
        //veterinario.setIdUsuario(id);
        
        return veterinario;
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
    
    private static List<ConsultaMedica> preencheConsulta(Veterinario veterinario){
        
        Exame exame = preencheExame();
        Pet pet = preenchePet();
        
        ConsultaMedica consulta = new ConsultaMedica();
        consulta.setDataMarcada(Date.from(Instant.now()));
        consulta.setDiagnostico("Doente por demais");
        consulta.setExame(exame);
        consulta.setPet(pet);
        consulta.setStatus(StatusConsulta.MARCADA);
        consulta.setVeterinario(veterinario);
        
        List<ConsultaMedica> listaConsultas = new ArrayList<>();
        
        listaConsultas.add(consulta);
        
        
        return listaConsultas;
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
 
        cliente.setEmail("cliente@cli.com");
        cliente.setEndereco(endereco);
        cliente.setLogin("cliente_gastador");
        cliente.setNome("Cliente cli");
        cliente.setSenha("cliente123");
        
        return cliente;
    }

}
