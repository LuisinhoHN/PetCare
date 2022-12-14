/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapetshop.negocio;

import static br.com.sistemapetshop.acesso.Papel.ADMINISTRADOR;
import static br.com.sistemapetshop.acesso.Papel.CLIENTE;
import static br.com.sistemapetshop.acesso.Papel.FUNCIONARIO;
import br.com.sistemapetshop.model.Grupo;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author jonathanpereira
 */
@Stateless
@DeclareRoles({ADMINISTRADOR, FUNCIONARIO, CLIENTE})
public class GrupoService extends Service<Grupo> {

    public GrupoService() {
        super(Grupo.class);
    }

    @PermitAll
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Grupo getGrupo(String[] grupos) {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<Grupo> query = em.createQuery("SELECT g FROM Grupo g WHERE g.strNome = ?1", Grupo.class);

            int i = 1;
            for (String grupo : grupos) {
                query.setParameter(i++, grupo);
            }

            return query.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
