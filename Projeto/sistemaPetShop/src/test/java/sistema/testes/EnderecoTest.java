/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.testes;

import com.sistema.model.Cliente;
import com.sistema.model.Endereco;
import com.sistema.model.Pet;
import java.util.ArrayList;
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
public class EnderecoTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction et;
    
    public EnderecoTest() {
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
    public void criaEnderecoValidoTeste() {
        Endereco endereco = new Endereco();
        Cliente cliente = new Cliente();
        List<Pet> listaPet = new ArrayList<>();

        cliente.setListaPet(listaPet);
        cliente.setEmail("cliente@cli.com");
        cliente.setEndereco(endereco);
        cliente.setLogin("Cliente");
        cliente.setNome("Cliente cli");
        cliente.setSenha("cliente123");

        endereco.setBairro("Aquele Bairro");
        endereco.setCep("12345678");
        endereco.setComplemento("Perto daquele Restaurante");
        endereco.setLogradouro("Avenida");
        endereco.setNumero(123);
        endereco.setUsuario(cliente);

        em.persist(endereco);
        em.flush();
        
        assertNotNull(endereco.getIdEndereco());
        
    }
    
/*<<<<<<< HEAD*/
    /*@Test
=======
    @Test
>>>>>>> 8f74a186454cf45c420ac550bee88013a78b9330
    public void criaEnderecoInvalidoTeste() {
        Endereco enderecoInvalido = new Endereco();
        Cliente cliente = new Cliente();

        List<Pet> listaPet = new ArrayList<>();

        cliente.setListaPet(listaPet);
        cliente.setEmail("cliente@cli.com");
        cliente.setEndereco(enderecoInvalido);
        cliente.setLogin("Cliente");
        cliente.setNome("Cliente cli");
        cliente.setSenha("cliente123");

        enderecoInvalido.setBairro("Aquele Bairro");
        enderecoInvalido.setCep(null);//valor muito grande
        enderecoInvalido.setComplemento("Perto daquele Restaurante");
        enderecoInvalido.setLogradouro("Avenida");
        enderecoInvalido.setNumero(123);
        enderecoInvalido.setUsuario(null);//Vazio

        em.persist(enderecoInvalido);
        et.commit();
        
        assertNull(enderecoInvalido.getIdEndereco());
        
    }*/
    
    /*
    @Test
    public void atualizaEnderecoValidoTeste() {
        Endereco enderecoAtt = new Endereco();
        Endereco endereco = new Endereco();
        Cliente cliente = new Cliente();
        List<Pet> listaPet = new ArrayList<>();

        cliente.setListaPet(listaPet);
        cliente.setEmail("cliente@cli.com");
        cliente.setEndereco(endereco);
        cliente.setLogin("Cliente");
        cliente.setNome("Cliente cli");
        cliente.setSenha("cliente123");

        endereco.setBairro("Aquele Bairro");
        endereco.setCep("12345678");
        endereco.setComplemento("Perto daquele Restaurante");
        endereco.setLogradouro("Avenida");
        endereco.setNumero(123);
        endereco.setUsuario(cliente);
        
        enderecoAtt = endereco;
        enderecoAtt.setNumero(546);
        em.merge(endereco);
        et.commit();
        
        enderecoAtt = em.find(Endereco.class, enderecoAtt.getIdEndereco());
        
        assertEquals(new Long(546), new Long(enderecoAtt.getNumero()));
    }
    */

    /*@Test
    public void atualizaEnderecoInvalidoTeste() {
        Endereco enderecoAtt = new Endereco();
        Endereco endereco = new Endereco();
        Cliente cliente = new Cliente();
        List<Pet> listaPet = new ArrayList<>();

        cliente.setListaPet(listaPet);
        cliente.setEmail("cliente@cli.com");
        cliente.setEndereco(endereco);
        cliente.setLogin("Cliente");
        cliente.setNome("Cliente cli");
        cliente.setSenha("cliente123");

        endereco.setBairro("Aquele Bairro");
        endereco.setCep("123456789");
        endereco.setComplemento("Perto daquele Restaurante");
        endereco.setLogradouro("Avenida");
        endereco.setNumero(123);
        endereco.setUsuario(cliente);
        
        enderecoAtt = endereco;
        enderecoAtt.setCep("546852741987");//Cep muito grande
        em.merge(enderecoAtt);
        et.commit();
        
        assertNotEquals(endereco.getCep(), enderecoAtt.getCep());
    }*/
    
    @Test
    public void deletaEnderecoTeste(){
        Query query = em.createQuery("from Endereco e where e.logradouro like :logradouro ", Endereco.class);
        query.setParameter("logradouro", "Casa3");
        Endereco endereco = (Endereco) query.getSingleResult();

        em.remove(endereco);
        em.flush();

        endereco = em.find(Endereco.class, endereco.getIdEndereco());

        assertNull(endereco);
        
        assertTrue(true);
    }
    
    /* 
    @Test
    public void deletarEnderecoQueryTest() {

        Logger.getGlobal().log(Level.INFO, "deletarEnderecoTest");
        Long id = 1L;
        Query query = em.createQuery("DELETE FROM Endereco AS e WHERE e.idEndereco = ?1");
        query.setParameter(1, id);
        query.executeUpdate();

        Endereco endereco = em.find(Endereco.class, id);
        assertNull(endereco);

    }
    */
    
    /* OK */
    @Test
    public void atualizarFuncionarioQueryTest() {
        Logger.getGlobal().log(Level.INFO, "atualizarFuncionarioQueryTest");
        
        Long id = 2L;
        Query query = em.createQuery("UPDATE Endereco AS e SET e.logradouro = ?1 WHERE e.idEndereco = ?2");
        
        query.setParameter(1, "CasaNova");
        query.setParameter(2, id);
        query.executeUpdate();
        
        Endereco endereco = em.find(Endereco.class, id);
        
        assertEquals("CasaNova", endereco.getLogradouro());
     
    }

    @Test
    public void selectSqlNativeNamedQueryTeste() {
        TypedQuery<Endereco> query = em.createNamedQuery("Endereco.PorLogradouro", Endereco.class);
        query.setParameter(1,"Casa3");
        
        List<Endereco> listaEnderecos = query.getResultList();

        assertEquals(1, listaEnderecos.size());
        
        assertTrue(true);
    }

}
