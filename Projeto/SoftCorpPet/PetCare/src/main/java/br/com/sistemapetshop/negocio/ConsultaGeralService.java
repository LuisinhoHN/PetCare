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
import br.com.sistemapetshop.model.ConsultaGeral;
import br.com.sistemapetshop.model.Endereco;
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
 * @author joanthanpereira
 */
@Stateless
@DeclareRoles({ADMINISTRADOR, FUNCIONARIO, CLIENTE})
public class ConsultaGeralService extends Service<ConsultaGeral> {

    public ConsultaGeralService() {
        super(ConsultaGeral.class);
    }
    
    @RolesAllowed({CLIENTE, FUNCIONARIO, ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void salvar(ConsultaGeral consultaGeral) {
        EntityManager em = getEntityManager();
        em.persist(consultaGeral);
    }

    @RolesAllowed({CLIENTE, FUNCIONARIO, ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void atualizar(ConsultaGeral consultaGeral) {
        EntityManager em = getEntityManager();
        em.merge(consultaGeral);
    }

    @RolesAllowed({ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void remover(ConsultaGeral consultaGeral) {
        EntityManager em = getEntityManager();
        em.remove(em.merge(consultaGeral));
    }

    @RolesAllowed({CLIENTE, FUNCIONARIO, ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public List<ConsultaGeral> listar() {
        return getEntidades(ConsultaGeral.TODOS);
    }

}
