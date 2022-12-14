/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapetshop.negocio;

import static br.com.sistemapetshop.acesso.Papel.ADMINISTRADOR;
import static br.com.sistemapetshop.acesso.Papel.FUNCIONARIO;
import br.com.sistemapetshop.model.Funcionario;
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
@DeclareRoles({ADMINISTRADOR, FUNCIONARIO})
public class FuncionarioService extends Service<Funcionario> {

    public FuncionarioService() {
        super(Funcionario.class);
    }

    @RolesAllowed({ADMINISTRADOR, FUNCIONARIO})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void salvar(Funcionario funcionario) {
        EntityManager em = getEntityManager();
        em.persist(funcionario);
    }

    @RolesAllowed({FUNCIONARIO, ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void atualizar(Funcionario funcionario) {
        EntityManager em = getEntityManager();
        em.merge(funcionario);
    }

    @RolesAllowed({ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void remover(Funcionario funcionario) {
        EntityManager em = getEntityManager();
        em.remove(em.merge(funcionario));
    }

    @RolesAllowed({ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public List<Funcionario> listar() {
        return getEntidades(Funcionario.TODOS);
    }
}
