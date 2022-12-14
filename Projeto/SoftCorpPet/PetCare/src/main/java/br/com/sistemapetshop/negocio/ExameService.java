/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapetshop.negocio;

import static br.com.sistemapetshop.acesso.Papel.ADMINISTRADOR;
import static br.com.sistemapetshop.acesso.Papel.VETERINARIO;
import static br.com.sistemapetshop.acesso.Papel.CLIENTE;
import static br.com.sistemapetshop.acesso.Papel.FUNCIONARIO;
import br.com.sistemapetshop.model.Exame;
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
public class ExameService extends Service<Exame> {

    public ExameService() {
        super(Exame.class);
    }

    @RolesAllowed({ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void salvar(Exame exame) {
        EntityManager em = getEntityManager();
        em.persist(exame);
    }

    @RolesAllowed({ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void atualizar(Exame exame) {
        EntityManager em = getEntityManager();
        em.merge(exame);
    }

    @RolesAllowed({ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void remover(Exame exame) {
        EntityManager em = getEntityManager();
        em.remove(em.merge(exame));
    }

    @RolesAllowed({CLIENTE, FUNCIONARIO, ADMINISTRADOR, VETERINARIO})
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public List<Exame> listar() {
        return getEntidades(Exame.TODOS);
    }
}
