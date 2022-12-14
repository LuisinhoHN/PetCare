/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapetshop.negocio;

import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author jonathanpereira
 * @param <Entidade>
 */
public abstract class Service<Entidade> {

    @PersistenceContext(unitName = "corporativoPU")
    private EntityManager em;

    private final Class<Entidade> classe;

    public EntityManager getEntityManager() {
        return em;
    }

    public Service(Class<Entidade> classe) {
        this.classe = classe;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void salvar(Entidade entidade) {
        em.persist(entidade);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void atualizar(Entidade entidade) {
        em.merge(entidade);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void remover(Entidade entidade) {
        em.remove(em.merge(entidade));
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Entidade buscar(Long idEntidade) {
        return em.find(classe, idEntidade);
    }

    @PermitAll /* Provisório :) */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Entidade> listar() {
        CriteriaQuery criteria = em.getCriteriaBuilder().createQuery();
        criteria.select(criteria.from(classe));

        //return (List<Entidade>) em.createQuery("From " + classe + " c").getResultList();
        return em.createQuery(criteria).getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<Entidade> getEntidades(String nomeQuery) {
        TypedQuery<Entidade> query = em.createNamedQuery(nomeQuery, classe);
        return query.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected List<Entidade> getEntidades(String nomeQuery, Object[] parametros) {
        TypedQuery<Entidade> query = em.createNamedQuery(nomeQuery, classe);

        int i = 1;
        for (Object parametro : parametros) {
            query.setParameter(i++, parametro);
        }

        return query.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected Entidade getEntidade(String nomeQuery, Object[] parametros) {
        TypedQuery<Entidade> query = em.createNamedQuery(nomeQuery, classe);

        int i = 1;
        for (Object parametro : parametros) {
            query.setParameter(i++, parametro);
        }

        return query.getSingleResult();
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected Boolean checarExistencia(String nomeQuery, Object parametro) {
        Entidade entidade;

        entidade = getEntidade(nomeQuery, new Object[]{parametro});
        if (entidade != null) {
            return true;
        } else {
            return false;
        }
    }

}
