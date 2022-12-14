/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapetshop.negocio;

import static br.com.sistemapetshop.acesso.Papel.ADMINISTRADOR;
import static br.com.sistemapetshop.acesso.Papel.CLIENTE;
import static br.com.sistemapetshop.acesso.Papel.FUNCIONARIO;
import br.com.sistemapetshop.model.Servico;
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

@DeclareRoles({ADMINISTRADOR, FUNCIONARIO, CLIENTE})
public class ServicoService extends Service<Servico> {

    public ServicoService() {
        super(Servico.class);
    }
    
    @RolesAllowed({ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void salvar(Servico servico) {
        EntityManager em = getEntityManager();
        em.persist(servico);
    }
   
    @RolesAllowed({ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void atualizar(Servico servico) {
        EntityManager em = getEntityManager();
        em.merge(servico);
    }

    @RolesAllowed({ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void remover(Servico servico) {
        EntityManager em = getEntityManager();
        em.remove(em.merge(servico));
    }
    
    @RolesAllowed({CLIENTE, FUNCIONARIO, ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public List<Servico> listar() {
        return getEntidades(Servico.TODOS);
    }
}
