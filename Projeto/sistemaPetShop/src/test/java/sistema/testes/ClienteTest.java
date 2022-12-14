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
import com.sistema.model.Pet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Usuario
 *
 */
public class ClienteTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction et;
    private static Logger logger;

    public ClienteTest() {
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
    @Test
    public void criarCartaoValidoEmTest() {

        Cliente cliente = new Cliente();
        List<Cartao> listaCartao = new ArrayList<>();
        
        Cartao cartao = new Cartao();
        cartao.setBandeira("Dinners Club");
        cartao.setNumero("5559293458709012");
        Calendar calendario = Calendar.getInstance();
        calendario.set(2020, 12, 5);
        cartao.setDataValidade(calendario.getTime());

        listaCartao.add(cartao);
        cliente.setCartao(listaCartao); 
        
        List<Pet> listaPet = new ArrayList<>();
        cliente.setListaPet(listaPet);
        
        cliente.setEmail("Spock@enterprise.com");
        cliente.setLogin("Spock");
        cliente.setNome("Spock");
        cliente.setSenha("passwordspock");
        
        em.persist(cliente);

        et.commit();

        cliente = em.find(Cliente.class, cliente.getIdUsuario());
        assertNotNull(cliente.getIdUsuario());
    }

    // OK
    @Test
    public void atualizarClienteValidoEmTest() {

        Long idCliente = 1L;
        Cliente cliente = em.find(Cliente.class, idCliente);
        
        List<Cartao> listaCartao = new ArrayList<>();
        
        Cartao cartao = new Cartao();
        cartao.setBandeira("American Express");
        cartao.setNumero("9999293458709333");
        Calendar calendario = Calendar.getInstance();
        calendario.set(2018, 10, 25);
        cartao.setDataValidade(calendario.getTime());

        listaCartao.add(cartao);
        cliente.setCartao(listaCartao); 
        
        List<Pet> listaPet = new ArrayList<>();
        cliente.setListaPet(listaPet);
        
        cliente.setEmail("Kirk@starfleet.enterprise.com");
        cliente.setLogin("JimKirk1701");
        cliente.setNome("James T. Kirk");
        cliente.setSenha("pwKirk");
        
        em.merge(cliente);
        em.flush();

        Cliente clienteBd = em.find(Cliente.class, idCliente);
        
        assertNotNull(cliente.getIdUsuario());
        assertEquals(cliente.getLogin(), clienteBd.getLogin() ); 
    }

    /* OK */
    @Test
    public void deletarClienteEmTest() {

        Logger.getGlobal().log(Level.INFO, "deletarClienteTest");
        Long id = 1L;
        TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c WHERE c.idUsuario = :id", Cliente.class);
        query.setParameter("id", id);
        Cliente cliente = query.getSingleResult();

        em.remove(cliente);
        //et.commit();
        em.flush();
        
        cliente = em.find(Cliente.class, cliente.getIdUsuario());
        assertNull(cliente);

    }

    /* XXXXXXXXXXXXXX FAIL */
    @Test
    public void deletarClienteQueryTest() {

        Logger.getGlobal().log(Level.INFO, "deletarClienteQueryTest");
        Long id = 1L;
        
        Query query1 = em.createQuery("DELETE FROM Usuario AS u WHERE u.idUsuario = ?1");
        query1.setParameter(1, id);
        query1.executeUpdate();
        
        Query query2 = em.createQuery("DELETE FROM Cliente AS c WHERE c.idUsuario = ?1");
        query2.setParameter(1, id);
        query2.executeUpdate();

        Cliente cliente = em.find(Cliente.class, id);
        assertNull(cliente);

    }

    /* OK */
    @Test
    public void atualizarClienteQueryTest() {
        Logger.getGlobal().log(Level.INFO, "atualizarClienteQueryTest");

        Long id = 1L;
        Query query = em.createQuery("UPDATE Cliente AS c SET c.login = ?1 WHERE c.idUsuario = ?2");

        String login = "AllanSFreitas";

        query.setParameter(1, login);
        query.setParameter(2, id);
        query.executeUpdate();

        Cliente cliente = em.find(Cliente.class, id);

        assertEquals(login, cliente.getLogin());

    }

    // FAIL XXXXXXXXXxx
    @Test
    public void selectSqlNativeNamedQueryTest() {
        TypedQuery<Cliente> query = em.createNamedQuery("Cliente.PorLoginSQL", Cliente.class);
        query.setParameter(1, "AllanSFreitas");

        List<Cliente> listaCliente = query.getResultList();

        assertEquals(1, listaCliente.size()); // Retonra apenas 1 cliente (Allan)
    }

    // OK
    @Test
    public void selectNamedQueryTest() {
        Long id = 1L;
        TypedQuery<Cliente> query = em.createNamedQuery("Cliente.PorId", Cliente.class);

        query.setParameter(1, id);
        Cliente cliente = query.getSingleResult();

        assertEquals(cliente.getIdUsuario(), id); // Pegou o id

    }


}
