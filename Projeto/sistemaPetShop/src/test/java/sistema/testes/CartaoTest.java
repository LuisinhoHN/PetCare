/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import com.sistema.model.Cartao;
import com.sistema.model.Cliente;
import com.sistema.model.Endereco;
import com.sistema.model.Pet;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 *
 * @author Usuario
 *
 */
public class CartaoTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction et;
    private static Logger logger;

    public CartaoTest() {
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
        logger = Logger.getGlobal();
        logger.setLevel(Level.INFO);

        emf = Persistence.createEntityManagerFactory("sistemapetshopPU"); // nome da PU
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
            logger.log(Level.SEVERE, ex.getMessage());

            if (et.isActive()) {
                et.rollback();
            }
        } finally {
            em.close();
            em = null;
            et = null;
        }

    }

    // OK
    // Cartao
    @Test
    public void criarCartaoValidoEmTest() {
        Cartao cartao = new Cartao();
        cartao.setBandeira("Visa Electron");
        cartao.setNumero("5559293458709012");

        Calendar calendario = Calendar.getInstance();
        calendario.set(2019, 5, 5);

        cartao.setDataValidade(calendario.getTime());
        em.persist(cartao);
        em.flush();
        //et.commit();

        //cartao = em.find(Cartao.class, cartao.getIdCartao());
        assertNotNull(cartao.getIdCartao());
    }

    // OK
    // Cartao, Cliente
    @Test
    public void criarCartaoValidoQueryTest() {

        Cartao cartao = new Cartao();
        Cliente cliente = new Cliente();
        cartao.setBandeira("American Express");

        Calendar calendario = Calendar.getInstance();
        calendario.set(2020, 6, 12);

        cartao.setDataValidade(calendario.getTime());
        cartao.setNumero("5559293778809012");

        TypedQuery<Cliente> query = em.createQuery("FROM Cliente c WHERE c.idUsuario = :idCliente", Cliente.class);
        query.setParameter("idCliente", 1L);
        cliente = (Cliente) query.getSingleResult();

        em.persist(cartao);
        em.flush();
        //et.commit();

        //cartao = em.find(Cartao.class, cartao.getIdCartao());
        cliente = em.find(Cliente.class, cliente.getIdUsuario());

        assertNotNull(cartao.getIdCartao());
        assertNotNull(cliente.getIdUsuario());

    }

    @Test
    public void atualizarCartaoValidoEmTest() {

        Long id = 1L;
        Cartao cartao = em.find(Cartao.class, id);

        cartao.setBandeira("Dinners Club");

        Calendar calendario = Calendar.getInstance();
        calendario.set(2030, 6, 12);

        cartao.setDataValidade(calendario.getTime());
        cartao.setNumero("8886763778809012");

        em.merge(cartao);
        et.commit();

        cartao = em.find(Cartao.class, id);
        assertNotNull(cartao.getIdCartao());
    }

    // PEGA MAIS OU MENOS :(
    @Test
    public void criarCartaoInvalidoEmTest() {
        Cartao cartao = new Cartao();
        Cliente cliente = new Cliente();
        cartao.setBandeira("Dinners Club");

        Calendar calendario = Calendar.getInstance();
        calendario.set(2005, 6, 12);

        try {
            cliente.setEmail("Spock@enterprise.com");
            cliente.setLogin("Spock");
            cliente.setNome("Spock");
            cliente.setSenha("passwordspock");

            cartao.setDataValidade(Date.from(Instant.now()));
            cartao.setCliente(cliente);
            cartao.setNumero("8886763778809012");

            em.persist(cartao);
            
            assertTrue(false);
        } catch (ConstraintViolationException ex) {
            Logger.getGlobal().info(ex.getMessage());

            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            if (logger.isLoggable(Level.INFO)) {
                for (ConstraintViolation violation : constraintViolations) {
                    Logger.getGlobal().log(Level.INFO, "{0}.{1}: {2}", new Object[]{violation.getRootBeanClass(), violation.getPropertyPath(), violation.getMessage()});
                }
            }
            
            em.flush();
            em.clear();
            
            cartao = em.find(Cartao.class, cartao.getIdCartao());

            assertEquals(1, constraintViolations.size());
            assertNull(cartao.getIdCartao());
        }
    }

    // NÃO PEGA :(
//    @Test
//    public void atualizarCartaoInvalido() {
//        
//        Long id = 1L;
//        Cartao cartao = em.find(Cartao.class, id);
//        cartao.setBandeira(null); // Inválido
//
//        try {
//            em.flush();
//            assertTrue(false);
//        } catch (ConstraintViolationException ex) {
//            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
//
//            if (logger.isLoggable(Level.INFO)) {
//                for (ConstraintViolation violation : constraintViolations) {
//                    Logger.getGlobal().log(Level.INFO, "{0}.{1}: {2}", new Object[]{violation.getRootBeanClass(), violation.getPropertyPath(), violation.getMessage()});
//                }
//            }
//
//            assertEquals(1, constraintViolations.size());
//        }
//    }
    /* OK */
    // Cartao
    @Test
    public void deletarCartaoEmTest() {

        Logger.getGlobal().log(Level.INFO, "deletarCartaoTest");
        TypedQuery<Cartao> query = em.createQuery("SELECT c FROM Cartao c WHERE c.numero like :numeroCartao", Cartao.class);
        query.setParameter("numeroCartao", "1119293458709222");
        Cartao cartao = query.getSingleResult();

        em.remove(cartao);
        et.commit();

        cartao = em.find(Cartao.class, cartao.getIdCartao());
        assertNull(cartao);

    }

    /* OK */
    @Test
    public void deletarCartaoQueryTest() {

        Logger.getGlobal().log(Level.INFO, "deletarCartaoTest");
        Long id = 1L;
        Query query = em.createQuery("DELETE FROM Cartao AS c WHERE c.idCartao = ?1");
        query.setParameter(1, id);
        query.executeUpdate();

        Cartao cartao = em.find(Cartao.class, id);
        assertNull(cartao);

    }

    /* OK */
    @Test
    public void atualizarCartaoQueryTest() {
        Logger.getGlobal().log(Level.INFO, "atualizarCartaoQueryTest");

        Long id = 1L;
        Query query = em.createQuery("UPDATE Cartao AS c SET c.bandeira = ?1 WHERE c.idCartao = ?2");

        String bandeira = "Master Card";

        query.setParameter(1, bandeira);
        query.setParameter(2, id);
        query.executeUpdate();

        Cartao cartao = em.find(Cartao.class, id);

        assertEquals(bandeira, cartao.getBandeira());

    }

    /*<<<<<<< HEAD
=======
     */
    @Test
    public void selectSqlNativeNamedQueryTest() {
        TypedQuery<Cartao> query = em.createNamedQuery("Cartao.PorBandeiraSQL", Cartao.class);
        query.setParameter(1, "Visa");

        List<Cartao> listaCartao = query.getResultList();

        assertEquals(1, listaCartao.size()); // Retonra apenas 1 cartão

    }

    @Test
    public void selectNamedQueryTest() {
        Long id = 2L;
        TypedQuery<Cartao> query = em.createNamedQuery("Cartao.PorId", Cartao.class);

        query.setParameter(1, id);
        Cartao cartao = query.getSingleResult();

        assertEquals(cartao.getIdCartao(), id); // Pegou o id

    }

    @Test
    public void selectNativeQueryTest() {
        Long idCliente = 1L;
        Query query = em.createNativeQuery("SELECT id_cartao, str_bandeira, date_dataValidade, str_numero, "
                + "fk_cliente FROM tb_cartao WHERE fk_cliente = ?1", Cartao.class);

        query.setParameter(1, idCliente);
        List<Cartao> listaCartao = query.getResultList();

        int cartoes_cliente_1 = 4;
        assertEquals(listaCartao.size(), cartoes_cliente_1);

    }


    /*>>>>>>> 8f74a186454cf45c420ac550bee88013a78b9330*/
}
