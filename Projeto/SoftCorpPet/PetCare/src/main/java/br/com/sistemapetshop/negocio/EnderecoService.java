/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapetshop.negocio;

import static br.com.sistemapetshop.acesso.Papel.ADMINISTRADOR;
import static br.com.sistemapetshop.acesso.Papel.CLIENTE;
import static br.com.sistemapetshop.acesso.Papel.FUNCIONARIO;
import br.com.sistemapetshop.model.Endereco;
import br.com.sistemapetshop.model.Servico;
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
@DeclareRoles({ADMINISTRADOR, FUNCIONARIO, CLIENTE})
public class EnderecoService extends Service<Endereco> {

    public EnderecoService() {
        super(Endereco.class);
    }
    
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void salvar(Endereco endereco) {
        EntityManager em = getEntityManager();
        em.persist(endereco);
    }
   
    @RolesAllowed({CLIENTE, FUNCIONARIO, ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void atualizar(Endereco endereco) {
        EntityManager em = getEntityManager();
        em.merge(endereco);
    }

    @RolesAllowed({ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void remover(Endereco endereco) {
        EntityManager em = getEntityManager();
        em.remove(em.merge(endereco));
    }
    
    @RolesAllowed({CLIENTE, FUNCIONARIO, ADMINISTRADOR})
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public List<Endereco> listar() {
        return getEntidades(Endereco.TODOS);
    }

}