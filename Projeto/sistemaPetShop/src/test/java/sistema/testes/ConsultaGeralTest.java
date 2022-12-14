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
import com.sistema.model.ConsultaGeral;
import com.sistema.model.Endereco;
import com.sistema.model.EspecialidadeFuncionario;
import com.sistema.model.Funcionario;
import com.sistema.model.Pet;
import com.sistema.model.Servico;
import com.sistema.model.StatusConsulta;
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
public class ConsultaGeralTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction et;
    private static Logger logger;

    public ConsultaGeralTest() {
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
    public void criarConsultaGeralValidaEmTest() {

        ConsultaGeral consultaGeral = new ConsultaGeral();
        Servico servico = new Servico();
        servico.setNome("Banho completo");
        servico.setValor(99.00);

        Calendar calendario = Calendar.getInstance();
        calendario.set(2017, 5, 6);

        consultaGeral.setDataMarcada(calendario.getTime());
        Funcionario funcionario = new Funcionario();
        funcionario.setEmail("func@gmail.com");
        funcionario.setLogin("Peter222");
        funcionario.setNome("Peter");
        funcionario.setSenha("peterPw");

        Endereco endereco = new Endereco();
        endereco.setBairro("Pequenópolis");
        endereco.setCep("66666667");
        endereco.setComplemento("aquele lugar");
        endereco.setLogradouro("Rua dos feirantes");
        endereco.setNumero(222);
        endereco.setUsuario(funcionario);

        funcionario.setEndereco(endereco);
        funcionario.setEspecialidadeFuncionario(EspecialidadeFuncionario.BANHISTA);

        consultaGeral.setFuncionario(funcionario);
        consultaGeral.setServico(servico);
        consultaGeral.setStatus(StatusConsulta.MARCADA);

        em.persist(consultaGeral);
        //et.commit();
        em.flush();

        consultaGeral = em.find(ConsultaGeral.class, consultaGeral.getIdConsulta());
        assertNotNull(consultaGeral.getIdConsulta());
    }

    // OK
    @Test
    public void atualizarConsultaGeralValidaEmTest() {

        Long idConsultaGeral = 1L;
        ConsultaGeral consultaGeral = em.find(ConsultaGeral.class, idConsultaGeral);

        Servico servico = new Servico();
        servico.setNome("tosa completa c/ banho");
        servico.setValor(100.00);

        Calendar calendario = Calendar.getInstance();
        calendario.set(2017, 10, 10);

        consultaGeral.setDataMarcada(calendario.getTime());
        Funcionario funcionario = new Funcionario();
        funcionario.setEmail("peterAllen@yahoo.com.br");
        funcionario.setLogin("PeterPAllen");
        funcionario.setNome("Peter P. Allen");
        funcionario.setSenha("peterSenha");

        Endereco endereco = new Endereco();
        endereco.setBairro("SmallVille");
        endereco.setCep("555888");
        endereco.setComplemento("px. praça da república");
        endereco.setLogradouro("Rua dos pedreiros");
        endereco.setNumero(333);
        endereco.setUsuario(funcionario);

        funcionario.setEndereco(endereco);
        funcionario.setEspecialidadeFuncionario(EspecialidadeFuncionario.TOSADOR);

        consultaGeral.setFuncionario(funcionario);
        consultaGeral.setServico(servico);
        consultaGeral.setStatus(StatusConsulta.CONCLUIDA);

        em.merge(consultaGeral);
        em.flush();

        ConsultaGeral consultaGeralBd = em.find(ConsultaGeral.class, idConsultaGeral);

        assertNotNull(consultaGeral.getIdConsulta());
        assertEquals(consultaGeral.getDataMarcada(), consultaGeralBd.getDataMarcada());
    }

    /* OK */
    @Test
    public void deletarConsultaGeralBdEmTest() {

        Logger.getGlobal().log(Level.INFO, "deletarConsultaGeralBdEmTest");
        Long idConsulta = 1L;
        
        TypedQuery<ConsultaGeral> query = em.createQuery("SELECT c FROM ConsultaGeral c WHERE "
                + "c.idConsulta = :id", ConsultaGeral.class);
        query.setParameter("id", idConsulta);
        
        ConsultaGeral consultaGeral = query.getSingleResult();

        em.remove(consultaGeral);
        //et.commit();
        em.flush();

        consultaGeral = em.find(ConsultaGeral.class, consultaGeral.getIdConsulta() );
        assertNull(consultaGeral);

    }

    /* OK OK */
    @Test
    public void deletarConsultaGeralQueryTest() {

        Logger.getGlobal().log(Level.INFO, "deletarConsultaGeralQueryTest");
        Long idConsulta = 2L;

        Query query = em.createQuery("DELETE FROM ConsultaGeral AS c WHERE c.idConsulta = ?1");
        query.setParameter(1, idConsulta);
        query.executeUpdate();

        ConsultaGeral consultaGeralBd = em.find(ConsultaGeral.class, idConsulta);
        assertNull(consultaGeralBd);

    }

    /* XXXXXXXXXXXXXXX */
//    @Test
//    public void atualizarConsultaGeralQueryTest() {
//        Logger.getGlobal().log(Level.INFO, "atualizarConsultaGeralQueryTest");
//
//        Long idConsulta = 1L;
//        Query query = em.createQuery("UPDATE ConsultaGeral AS c SET c.servico = ?1 "
//                + "WHERE c.idConsulta = ?2");
//
//        Servico servico = new Servico();
//        servico.setNome("tosa parcial");
//        servico.setValor(60.00);
//        
//        List<ConsultaGeral> listaConsultaGeral = new ArrayList<>();
//        servico.setListaConsultaGeral(listaConsultaGeral);
//                
//        query.setParameter(1, servico);
//        query.setParameter(2, idConsulta);
//        query.executeUpdate();
//
//        ConsultaGeral consultaGeral = em.find(ConsultaGeral.class, idConsulta);
//
//        assertEquals(servico.getNome(), consultaGeral.getServico().getNome());
//
//    }

    // OK
    @Test
    public void selectNamedQueryTest() {
        Long id = 1L;
        TypedQuery<ConsultaGeral> query = em.createNamedQuery("ConsultaGeral.PorId", ConsultaGeral.class);

        query.setParameter(1, id);
        ConsultaGeral consultaGeral = query.getSingleResult();

        assertEquals(consultaGeral.getIdConsulta(), id); // Pegou o id

    }

}
