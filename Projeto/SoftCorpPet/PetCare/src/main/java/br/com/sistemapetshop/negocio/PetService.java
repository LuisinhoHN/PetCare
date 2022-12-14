/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapetshop.negocio;

import static br.com.sistemapetshop.acesso.Papel.ADMINISTRADOR;
import static br.com.sistemapetshop.acesso.Papel.CLIENTE;
import static br.com.sistemapetshop.acesso.Papel.FUNCIONARIO;
import br.com.sistemapetshop.model.Cliente;
import br.com.sistemapetshop.model.Pet;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jonathanpereira
 */
@Stateless
@DeclareRoles({ADMINISTRADOR, FUNCIONARIO, CLIENTE})
public class PetService extends Service<Pet> {

    public PetService() {
        super(Pet.class);
    }

    @RolesAllowed({CLIENTE, ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void salvar(Pet pet) {
        EntityManager em = getEntityManager();

        if (!checarExistencia(Pet.POR_NOME, pet.getNome())) {
            em.persist(pet);
        }
    }

    @RolesAllowed({CLIENTE, ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void atualizar(Pet pet) {
        EntityManager em = getEntityManager();
        em.merge(pet);
    }

    @RolesAllowed({CLIENTE, ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void remover(Pet pet) {
        EntityManager em = getEntityManager();
        em.remove(em.merge(pet));
    }

    @RolesAllowed({CLIENTE, ADMINISTRADOR, FUNCIONARIO})
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public List<Pet> listar() {
        return getEntidades(Pet.TODOS);
    }

    @RolesAllowed({CLIENTE, ADMINISTRADOR, FUNCIONARIO})
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Pet> listarPetsUsuario() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

        System.out.println("session: " + session.getAttribute("loginUsuarioSessao"));

        String loginUsuario = (String) session.getAttribute("loginUsuarioSessao");

        EntityManager em = getEntityManager();
        TypedQuery<Pet> query = em.createNamedQuery("Pet.porLoginUsuario", Pet.class);
//
         query.setParameter(1, loginUsuario);
        //TypedQuery<Pet> query = em.createQuery("FROM Pet p WHERE p.cliente.login like :loginCliente", Pet.class);
       // TypedQuery<Pet> query = em.createQuery("From Pet p where p.cliente = ?", Pet.class);
        
        //query.setParameter("loginCliente", clientePet.getLogin());

        List<Pet> listaPets = query.getResultList();

        return listaPets;
    }
}
