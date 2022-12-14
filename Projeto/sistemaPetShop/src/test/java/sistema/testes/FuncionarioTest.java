/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.testes;

import com.sistema.model.Endereco;
import com.sistema.model.Funcionario;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Usuario
 */
public class FuncionarioTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction et;

    public FuncionarioTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
        emf.close();
    }

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("sistemapetshopPU");
        DbUnitUtil.inserirDados();

        em = emf.createEntityManager();
        et = em.getTransaction();
        et.begin();
    }

    @After
    public void tearDown() {
        try {
            et.commit();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());

            if (et.isActive()) {
                et.rollback();
            }
        } finally {
            em.close();
            em = null;
            et = null;
        }
    }
    
    @Test
    public void criaFuncionarioValidoTeste() {
        Funcionario funcionario = new Funcionario();
        Endereco endereco = new Endereco();
        
        endereco.setBairro("Aquele Bairro");
        endereco.setCep("12345678");
        endereco.setComplemento("Perto daquele Restaurante");
        endereco.setLogradouro("Avenida");
        endereco.setNumero(123);
        
        funcionario.setNome("Funcionario");
        funcionario.setLogin("usuario");
        funcionario.setEmail("funcionario@gmail.com");
        funcionario.setSenha("funcionario123");
        funcionario.setEndereco(endereco);

        em.persist(funcionario);
        em.flush();
        
        assertNotNull(funcionario.getIdUsuario());
        
    }
    
    /*
    @Test
    public void criaFuncionarioInvalidoTeste() {
        
        em.persist(funcionario);
        et.commit();
        
        assertNull(funcionario.getIdUsuario());
        
    }
    */
    
    /*
    @Test
    public void atualizaFuncionarioValidoTeste() {
        
        em.merge(funcionario);
        et.commit();
        
        funcionario = em.find(Funcionario.class, funcionario.getIdUsuario());
        
        assertEquals();
    }
    */

    /*@Test
    public void atualizaFuncionarioInvalidoTeste() {
        
        em.merge(enderecoAtt);
        et.commit();
        
        assertNotEquals());
    }*/
    
    @Test
    public void deletaFuncionarioTeste(){
        Query query = em.createQuery("from Funcionario f where f.nome like :nome ", Funcionario.class);
        query.setParameter("nome", "Luis Henrique");
        Funcionario funcionario = (Funcionario) query.getSingleResult();

        em.remove(funcionario);
        em.flush();

        funcionario = em.find(Funcionario.class, funcionario.getIdUsuario());

        assertNull(funcionario);
        
        assertTrue(true);
    }

    @Test
    public void deletarFuncionarioEmTest() {

        Logger.getGlobal().log(Level.INFO, "deletarFuncionarioTest");
        TypedQuery<Funcionario> query = em.createQuery("SELECT f FROM Funcionario f WHERE f.nome like :nome", Funcionario.class);
        query.setParameter("nome", "Luis Henrique");
        Funcionario funcionario = query.getSingleResult();

        em.remove(funcionario);
        em.flush();

        funcionario = em.find(Funcionario.class, funcionario.getIdUsuario());
        assertNull(funcionario);

    }

    
    /* OK */
    @Test
    public void deletarFuncionarioQueryTest() {

        Logger.getGlobal().log(Level.INFO, "deletarFuncionarioTest");
        Long id = 1L;
        Query query = em.createQuery("DELETE FROM Funcionario AS f WHERE f.idUsuario = ?1");
        query.setParameter(1, id);
        query.executeUpdate();

        Funcionario funcionario = em.find(Funcionario.class, id);
        assertNull(funcionario);

    }
    
    
    /* OK */
    @Test
    public void atualizarFuncionarioQueryTest() {
        Logger.getGlobal().log(Level.INFO, "atualizarFuncionarioQueryTest");
        
        Long id = 2L;
        Query query = em.createQuery("UPDATE Funcionario AS f SET f.nome = ?1 WHERE f.idUsuario = ?2");
        
        query.setParameter(1, "Rodrigo");
        query.setParameter(2, id);
        query.executeUpdate();
        
        Funcionario funcionario = em.find(Funcionario.class, id);
        
        assertEquals("Rodrigo", funcionario.getNome());
     
    }

}
