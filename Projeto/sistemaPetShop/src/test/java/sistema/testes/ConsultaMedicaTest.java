/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class ConsultaMedicaTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction et;

    public ConsultaMedicaTest() {
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
    /* FUNCIONA */
    public void criaConsultaMedicaValidoTeste() {
        ConsultaMedica consultaMedica = new ConsultaMedica();
        List<ConsultaMedica> listaConsultas = new ArrayList<>();
        Veterinario veterinario = new Veterinario();
        Endereco endereco = new Endereco();
        Cliente cliente = new Cliente();
        Exame exame = new Exame();
        Pet pet = new Pet();

        consultaMedica.setDataMarcada(Date.from(Instant.now()));
        consultaMedica.setDiagnostico("Ele já está bom.");
        consultaMedica.setExame(exame);
        consultaMedica.setPet(pet);
        consultaMedica.setStatus(StatusConsulta.MARCADA);
        consultaMedica.setVeterinario(veterinario);

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

        em.persist(consultaMedica);
        em.flush();

        assertNotNull(consultaMedica.getIdConsulta());
        assertNotNull(veterinario.getIdUsuario());
        assertNotNull(cliente.getIdUsuario());
        assertNotNull(endereco.getIdEndereco());
        assertNotNull(pet.getIdPet());
        assertNotNull(consulta.getIdConsulta());
        assertNotNull(exame.getIdExame());

    }

    @Test
    /* FUNCIONA */
    public void atualizaConsultaMedicaValidoTeste() {
        Query query = em.createNamedQuery("ConsultaMedica.PorDiagnostico", ConsultaMedica.class);
        query.setParameter("nome", "Exame%");

        ConsultaMedica consultaMedica = em.find(ConsultaMedica.class, 3L);

        assertNotNull(consultaMedica.getIdConsulta());

        em.clear();

        consultaMedica.setDiagnostico("Exame efetuado");

        em.merge(consultaMedica);
        em.flush();

        assertEquals(2, query.getResultList().size());
    }

    @Test
    /* FUNCIONA */
    public void deletaConsultaMedicaTeste() {

        Query query = em.createQuery("from ConsultaMedica conm where conm.diagnostico like :diagnostico", ConsultaMedica.class);
        query.setParameter("diagnostico", "Exame%");
        ConsultaMedica consultaMedica = (ConsultaMedica) query.getSingleResult();

        em.remove(consultaMedica);
        em.flush();
        //et.commit();

        consultaMedica = em.find(ConsultaMedica.class, consultaMedica.getIdConsulta());

        assertNull(consultaMedica);
    }

    @Test
    /*NÂO FUNCIONA */
    public void criaConsultaMedicaInvalidoTeste() {

    }

    @Test
    /* FUNCIONA */
    public void selectJpqlQueryTeste() {
        String sql = "from ConsultaMedica conm where conm.diagnostico like 'Ta bom%'";

        TypedQuery<ConsultaMedica> query = em.createQuery(sql, ConsultaMedica.class);

        ConsultaMedica consultaMedica = query.getSingleResult();

        assertNotNull(consultaMedica.getIdConsulta());
    }

    @Test
    /* NÃO FUNCIONA */
    public void selectJpqlNamedQueryTeste() {

        TypedQuery<ConsultaMedica> query = em.createNamedQuery("ConsultaMedica.PorDiagnosticoNamed", ConsultaMedica.class);
        query.setParameter(1, "Ta bom%");

        ConsultaMedica consultaMedica = (ConsultaMedica) query.getSingleResult();

        assertNotNull(consultaMedica);
    }

    @Test
    /* NÃO FUNCIONA */
    public void selectSqlNativeQueryTeste() {

        String sql = "select * from tb_consulta_medica conm join tb_usuario user on conm.fk_veterinario = user.id_usuario where conm.fk_veterinario = ?1";
        Query query = em.createNativeQuery(sql, ConsultaMedica.class);
        query.setParameter(1, 3L);

        List<ConsultaMedica> listaConsultaMedica = query.getResultList();

        for (int i = 0; i < listaConsultaMedica.size(); i++) {
            assertNotNull(listaConsultaMedica.get(i).getIdConsulta());
        }
        
        assertEquals(2, listaConsultaMedica.size());
    }

    @Test
    /* FUNCIONA */
    public void selectSqlNativeNamedQueryTeste() {

        Query query;
        query = em.createNamedQuery("ConsultaMedica.PorDiagnosticoNamed");
        query.setParameter(1, "Ta bom%");

        List<Object[]> listaConsultaMedica = query.getResultList();

        assertEquals(1, listaConsultaMedica.size());
    }

    @Test
    /* NÃO FUNCIONA */
    public void atualizaConsultaMedicaEmTeste() {

        Query query = em.createNamedQuery("ConsultaMedica.PorDiagnosticoNamed", ConsultaMedica.class);
        query.setParameter(1, "Exame%");

        ConsultaMedica consultaMedica = (ConsultaMedica) query.getSingleResult();

        assertNotNull(consultaMedica.getIdConsulta());

        em.clear();

        consultaMedica.setDiagnostico("Novo diagnostico");

        em.merge(consultaMedica);
        em.flush();

        assertEquals(0, query.getResultList().size());
    }

    @Test
    /* NÃO FUNCIONA */
    public void atualizaConsultaMedicaQueryTeste() {

        Query query = em.createQuery("UPDATE ConsultaMedica conm SET conm.diagnostico = 'Exame realizado' WHERE conm.diagnostico like ?1 ");

        String crmv = "Exame realizado";
        query.setParameter(1, "Exame realizado");
        query.executeUpdate();

        ConsultaMedica consultaMedica = em.find(ConsultaMedica.class, 4L);

        assertEquals(crmv, consultaMedica.getDiagnostico());

    }

    @Test
    /* NÃO FUNCIONA */
    public void deletaConsultaMedicaQueryTeste() {

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
