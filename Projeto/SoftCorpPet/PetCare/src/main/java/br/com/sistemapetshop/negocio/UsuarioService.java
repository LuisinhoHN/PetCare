/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapetshop.negocio;

import static br.com.sistemapetshop.acesso.Papel.ADMINISTRADOR;
import static br.com.sistemapetshop.acesso.Papel.CLIENTE;
import static br.com.sistemapetshop.acesso.Papel.FUNCIONARIO;
import static br.com.sistemapetshop.acesso.Papel.VETERINARIO;
import br.com.sistemapetshop.model.Servico;
import br.com.sistemapetshop.model.Usuario;
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
@DeclareRoles({ADMINISTRADOR, FUNCIONARIO, CLIENTE, VETERINARIO})
public class UsuarioService extends Service<Usuario> {

    public UsuarioService() {
        super(Usuario.class);
    }

    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void salvar(Usuario usuario) {
        EntityManager em = getEntityManager();
        em.persist(usuario);
    }

    @RolesAllowed({ADMINISTRADOR, VETERINARIO, FUNCIONARIO, CLIENTE})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void atualizar(Usuario usuario) {
        EntityManager em = getEntityManager();
        em.merge(usuario);
    }

    @RolesAllowed({ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void remover(Usuario usuario) {
        EntityManager em = getEntityManager();
        em.remove(em.merge(usuario));
    }

// Trash code |X|
//    @RolesAllowed({ADMINISTRADOR})
//    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
//    @Override
//    public List<Usuario> listar() {
//        return getEntidades(Usuario.TODOS);
//    }
    
}
