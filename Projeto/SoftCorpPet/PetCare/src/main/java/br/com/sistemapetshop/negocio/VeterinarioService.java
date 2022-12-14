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
import br.com.sistemapetshop.model.Veterinario;
import java.util.List;
import javax.annotation.security.DeclareRoles;
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
public class VeterinarioService extends Service<Veterinario> {

    public VeterinarioService() {
        super(Veterinario.class);
    }

    @RolesAllowed({ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void salvar(Veterinario veterinario) {
        EntityManager em = getEntityManager();
        em.persist(veterinario);
    }

    @RolesAllowed({VETERINARIO, ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void atualizar(Veterinario veterinario) {
        EntityManager em = getEntityManager();
        em.merge(veterinario);
    }

    @RolesAllowed({ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void remover(Veterinario veterinario) {
        EntityManager em = getEntityManager();
        em.remove(em.merge(veterinario));
    }

    @RolesAllowed({ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public List<Veterinario> listar() {
        return getEntidades(Veterinario.TODOS);
    }
}
