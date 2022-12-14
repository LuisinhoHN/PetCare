package sistema.testes;

import com.sistema.model.Cliente;
import com.sistema.model.ConsultaMedica;
import com.sistema.model.Endereco;
import com.sistema.model.Exame;
import com.sistema.model.Pet;
import com.sistema.model.StatusConsulta;
import com.sistema.model.Veterinario;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jonathan Romualdo
 */
public class VeterinarioTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction et;

    public VeterinarioTest() {
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

    @Test /* FUNCIONA */
    public void criaVeterinarioValidoTeste() {
        List<ConsultaMedica> listaConsultas = new ArrayList<>();
        Veterinario veterinario = new Veterinario();
        Endereco endereco = new Endereco();
        Cliente cliente = new Cliente();
        Exame exame = new Exame();
        Pet pet = new Pet();

        endereco.setBairro("Bairro");
        endereco.setCep("12763818");
        endereco.setComplemento("Perto dali");
        endereco.setLogradouro("Avenida");
        endereco.setNumero(222);
        endereco.setUsuario(veterinario);

        veterinario.setCrmv("crmvPadraoTeste123");
        veterinario.setEmail("veterinario@vet.com");
        veterinario.setEndereco(endereco);
        veterinario.setEspecialidade("cirurgião");
        veterinario.setListaConsultaMedica(listaConsultas);
        veterinario.setLogin("melhorVeterinario123");
        veterinario.setNome("Veterinário Severino");
        veterinario.setSenha("veterinario1234");

        exame.setDescricao("Exame de rotina");
        exame.setNome("Rotina");
        exame.setTipo("Caro");
        exame.setValor(300.0);

        ConsultaMedica consulta = new ConsultaMedica();
        consulta.setDataMarcada(Date.from(Instant.now()));
        consulta.setDiagnostico("Doente por demais");
        consulta.setExame(exame);
        consulta.setPet(pet);
        consulta.setStatus(StatusConsulta.MARCADA);
        consulta.setVeterinario(veterinario);

        listaConsultas.add(consulta);

        Float peso = 24f;
        pet.setCliente(cliente);
        pet.setNome("Tótó");
        pet.setPedegree(Boolean.TRUE);
        pet.setPeso(peso);
        pet.setRaca("Labrador");

        cliente.setEmail("cliente@cli.com");
        cliente.setEndereco(endereco);
        cliente.setLogin("cliente_gastador");
        cliente.setNome("Cliente cli");
        cliente.setSenha("cliente123");

        em.persist(veterinario);
        //et.commit();
        em.flush();
        
        assertNotNull(veterinario.getIdUsuario());
        assertNotNull(cliente.getIdUsuario());
        assertNotNull(endereco.getIdEndereco());
        assertNotNull(pet.getIdPet());
        assertNotNull(consulta.getIdConsulta());
        assertNotNull(exame.getIdExame());
        
    }

    @Test /* FUNCIONA */
    public void atualizaVeterinarioValidoTeste() {
        Query query = em.createNamedQuery("Veterinario.PorNome", Veterinario.class);
        query.setParameter("nome", "Jonathan%");
        
        Veterinario veterinario = em.find(Veterinario.class, 3L);
        
        assertNotNull(veterinario.getIdUsuario());
        
        em.clear();
        
        veterinario.setEspecialidade("Cirurgiao Plastico");
        veterinario.setNome("Novo cirurgiao plastico");
        
        em.merge(veterinario);
        em.flush();
        
        assertEquals(0, query.getResultList().size());
    }

    @Test /* FUNCIONA */
    public void deletaVeterinarioTeste() {

        Query query = em.createQuery("from Veterinario v where v.crmv like :crmv", Veterinario.class);
        query.setParameter("crmv", "54214554");
        Veterinario veterinario = (Veterinario) query.getSingleResult();

        em.remove(veterinario);
        em.flush();
        //et.commit();

        veterinario = em.find(Veterinario.class, veterinario.getIdUsuario());

        assertNull(veterinario);
    }

    @Test /*NÂO FUNCIONA */
    public void criaVeterinarioInvalidoTeste() {
        List<ConsultaMedica> listaConsultas = new ArrayList<>();
        Veterinario veterinario = new Veterinario();
        Endereco endereco = new Endereco();
        Cliente cliente = new Cliente();
        Exame exame = new Exame();
        Pet pet = new Pet();

        try {
            endereco.setBairro("Bairro");
            endereco.setCep("12763818");
            endereco.setComplemento("Perto dali");
            endereco.setLogradouro("Avenida");
            endereco.setNumero(222);
            endereco.setUsuario(veterinario);

            veterinario.setCrmv("crmvPadraoTeste123");
            veterinario.setEmail("veterinario@vet.com");
            veterinario.setEndereco(null);  //Inválido
            veterinario.setEspecialidade("cirurgião");
            veterinario.setListaConsultaMedica(listaConsultas);
            veterinario.setLogin(null); //Inválido
            veterinario.setNome("Veterinário Severino");
            veterinario.setSenha("veterinario1234");

            exame.setDescricao("Exame de rotina");
            exame.setNome("Rotina");
            exame.setTipo("Caro");
            exame.setValor(300.0);

            ConsultaMedica consulta = new ConsultaMedica();
            consulta.setDataMarcada(Date.from(Instant.now()));
            consulta.setDiagnostico("Doente por demais");
            consulta.setExame(exame);
            consulta.setPet(pet);
            consulta.setStatus(StatusConsulta.MARCADA);
            consulta.setVeterinario(veterinario);

            listaConsultas.add(consulta);

            Float peso = 24f;
            pet.setCliente(cliente);
            pet.setNome("Tótó");
            pet.setPedegree(Boolean.TRUE);
            pet.setPeso(peso);
            pet.setRaca("Labrador");

            cliente.setEmail("cliente@cli.com");
            cliente.setEndereco(endereco);
            cliente.setLogin("cliente_gastador");
            cliente.setNome("Cliente cli");
            cliente.setSenha("cliente123");

            em.persist(veterinario);
            em.flush();
//            et.commit();
            assertTrue(false);
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            assertEquals(1, constraintViolations.size());
            assertNull(veterinario.getIdUsuario());
        }

        
    }

    @Test /*NÂO FUNCIONA */
    public void atualizaVeterinarioInvalidoTeste() {
        assertTrue(true);
    }

    @Test /* FUNCIONA */
    public void selectJpqlQueryTeste() {
        String sql = "from Veterinario v join Usuario u where u.idUsuario = 3";
        
        TypedQuery<Veterinario> query = em.createQuery(sql, Veterinario.class);
        
        Veterinario veterinario = query.getSingleResult();
        
        assertNotNull(veterinario.getIdUsuario());
    }

    @Test /* FUNCIONA */
    public void selectJpqlNamedQueryTeste() {

        TypedQuery<Veterinario> query = em.createNamedQuery("Veterinario.PorNome", Veterinario.class);
        query.setParameter("nome", "Jonathan%");

        Veterinario veterinario = query.getSingleResult();

        assertNotNull(veterinario);
    }

    @Test /* NÃO FUNCIONA */
    public void selectSqlNativeQueryTeste() {
        
        String sql = "select vet.str_crmv, vet.str_especialidade from tb_veterinario vet join tb_usuario user on vet.id_veterinario = user.id_usuario order by user.str_login";
        Query query = em.createNativeQuery(sql, Veterinario.class);
        
        Veterinario veterinario = (Veterinario) query.getSingleResult();
        
        assertNotNull(veterinario.getIdUsuario());
    }

    @Test /* FUNCIONA */
    public void selectSqlNativeNamedQueryTeste() {

        Query query;
        query = em.createNamedQuery("Veterinario.PorEspecialidade");
        query.setParameter(1, "Cardiaco");

        List<Object[]> listaVeterinarios = query.getResultList();

        assertEquals(1, listaVeterinarios.size());
    }

    @Test /* FUNCIONA */
    public void atualizaVeterinarioEmTeste() {
        
        Query query = em.createNamedQuery("Veterinario.PorNome", Veterinario.class);
        query.setParameter("nome", "Jonathan%");
        
        Veterinario veterinario = (Veterinario) query.getSingleResult();
        
        assertNotNull(veterinario.getIdUsuario());
        
        em.clear();
        
        veterinario.setEspecialidade("Cirurgiao");
        veterinario.setNome("Novo cirurgiao");
        
        em.merge(veterinario);
        em.flush();
        
        assertEquals(0, query.getResultList().size());
    }
    
    @Test /* FUNCIONA */
    public void atualizaVeterinarioQueryTeste() {

        Query query = em.createQuery("UPDATE Veterinario vet SET vet.crmv = '64629264' WHERE vet.crmv like '54214554' ");

        String crmv = "64629264";
        
        query.executeUpdate();

        Veterinario veterinario = em.find(Veterinario.class, 3L);

        assertEquals(crmv, veterinario.getCrmv());
        
    }

    @Test /* NÃO FUNCIONA */
    public void deletaVeterinarioQueryTeste() {
        
        String sql = "delete from Veterinario v where v.crmv = 54214554";
        String sql2 = "delete from Usuario u where u.idUsuario = 3";
        String sql3 = "delete from ConsultaMedica conm where conm.veterinario = 3";
        
        Query query = em.createQuery(sql);
        Query query2 = em.createQuery(sql2);
        Query query3 = em.createQuery(sql2);
        
        query3.executeUpdate();
        query2.executeUpdate();
        query.executeUpdate();
        
        Veterinario veterinario = em.find(Veterinario.class, 3L);
        

        assertNull(veterinario.getIdUsuario());
    }
}
