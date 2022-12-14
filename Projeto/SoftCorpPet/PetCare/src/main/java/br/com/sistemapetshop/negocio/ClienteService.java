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
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;

/**
 *
 * @author jonathanpereira
 */
@Stateless
@DeclareRoles({ADMINISTRADOR, FUNCIONARIO, CLIENTE})
public class ClienteService extends Service<Cliente> {

    public ClienteService() { 
        super(Cliente.class);
    }
        
    @PermitAll 
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void salvar(Cliente cliente) {
        EntityManager em = getEntityManager();
        em.persist(cliente);
    }
   
    @RolesAllowed({CLIENTE,ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void atualizar(Cliente cliente) {
        EntityManager em = getEntityManager();
        em.merge(cliente);
    }

    @RolesAllowed({ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void remover(Cliente cliente) {
        EntityManager em = getEntityManager();
        em.remove(em.merge(cliente));
    }
    
    @RolesAllowed({ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public List<Cliente> listar() {
        return getEntidades(Cliente.TODOS);
    }
}

