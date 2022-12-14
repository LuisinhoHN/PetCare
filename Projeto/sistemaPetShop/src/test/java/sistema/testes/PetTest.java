package sistema.testes;

import com.sistema.model.Cliente;
import com.sistema.model.Endereco;
import com.sistema.model.Pet;
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
 * @author Jonathan Romualdo
 */
public class PetTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction et;

    public PetTest() {
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
    public void criaPetValidotest() {
        Endereco endereco = new Endereco();
        Cliente cliente = new Cliente();
        Pet pet = new Pet();

        Float peso = 24f;

        pet.setCliente(cliente);
        pet.setNome("Tótó");
        pet.setPedegree(Boolean.TRUE);
        pet.setPeso(peso);
        pet.setRaca("Labrador");

        cliente.setEmail("cliente2@cli.com");
        cliente.setEndereco(endereco);
        cliente.setLogin("cliente_gastador2");
        cliente.setNome("Cliente cli");
        cliente.setSenha("cliente123");

        endereco.setBairro("Bairro");
        endereco.setCep("12763818");
        endereco.setComplemento("Perto dali");
        endereco.setLogradouro("Avenida");
        endereco.setNumero(222);
        endereco.setUsuario(cliente);

        em.persist(pet);
        em.flush();
        
        assertNotNull(pet.getIdPet());

    }

    @Test /* NÃO FUNCIONA */
    public void criaPetInvalidoTest() {
        Endereco endereco = new Endereco();
        Cliente cliente = new Cliente();
        Pet pet = new Pet();

        Float peso = 24f;

        pet.setCliente(cliente);
        pet.setNome("Tótó");
        pet.setPedegree(Boolean.TRUE);
        pet.setPeso(peso);
        pet.setRaca("Labrador");

        cliente.setEmail("cliente2@cli.com");
        cliente.setEndereco(endereco);
        cliente.setLogin("cliente_gastador2");
        cliente.setNome("Cliente cli");
        cliente.setSenha("cliente123");

        endereco.setBairro("Bairro");
        endereco.setCep("12763818");
        endereco.setComplemento("Perto dali");
        endereco.setLogradouro("Avenida");
        endereco.setNumero(222);
        endereco.setUsuario(cliente);

        em.persist(pet);
        em.flush();
        
        assertNotNull(pet.getIdPet());
    }

    @Test /* FUNCIONA */
    public void atualizaPetValidoTest() {
        Query query = em.createNamedQuery("Pet.PorNome", Pet.class);
        query.setParameter("nome", "Mia%");

        Pet pet = em.find(Pet.class, 2L);

        assertNotNull(pet.getIdPet());

        em.clear();

        pet.setNome("Miava agora late");
        pet.setRaca("Uma nova raça");

        em.merge(pet);
        em.flush();
        em.clear();

        em.find(Pet.class, pet.getIdPet());

        assertEquals(1, query.getResultList().size());
    }

    @Test /* NÃO FUNCIONA */
    public void atualizaPetInvalidoTest() {

    }

    @Test /* FUNCIONA */
    public void deletaPetEmTest() {
        Query query = em.createQuery("from Pet p where p.idPet like :id", Pet.class);
        query.setParameter("id", 1);
        Pet pet = (Pet) query.getSingleResult();

        em.remove(pet);
        em.flush();

        pet = em.find(Pet.class, pet.getIdPet());

        assertNull(pet);
    }

    @Test /* FUNCIONA */
    public void selectJpqlQueryTeste() {
        String sql = "from Pet p where p.peso >= 15.25";

        TypedQuery<Pet> query = em.createQuery(sql, Pet.class);

        List<Pet> pet = query.getResultList();

        for (int i = 0; i < pet.size(); i++) {
            assertNotNull(pet.get(i).getIdPet());
        }

        assertEquals(2, pet.size());
    }

    @Test /* FUNCIONA */
    public void selectJpqlNamedQueryTeste() {
        TypedQuery<Pet> query = em.createNamedQuery("Pet.PorNome", Pet.class);
        query.setParameter("nome", "Mia%");

        List<Pet> listaPets = query.getResultList();

        for (Pet pet : listaPets) {
            assertNotNull(pet.getIdPet());
        }

        assertEquals(1, listaPets.size());
    }

    @Test /* FUNCIONA */
    public void selectNativeQueryTeste() {
        String sql = "select * from tb_pet pet where pet.fk_cliente = 1 order by pet.str_nome";
        Query query = em.createNativeQuery(sql, Pet.class);

        List<Pet> listaPets = (List<Pet>) query.getResultList();

        for (Pet servico : listaPets) {
            assertNotNull(servico.getIdPet());
        }
        
        assertEquals(2, listaPets.size());
    }

    @Test /* FUNCIONA */
    public void selectNativeNamedQueryTeste() {
        Query query;
        query = em.createNamedQuery("Pet.PorRaca");
        query.setParameter(1, "Dog%");

        List<Object[]> listaServicos = query.getResultList();

        assertEquals(1, listaServicos.size());
    }
    
    @Test /* FUNCIONA */
    public void atualizaPetQueryTeste(){
        Long id = 2L;
        Query query = em.createQuery("UPDATE Pet pet SET pet.nome = ?1 WHERE pet.idPet <= ?2");

        String novoNome = "Novo Pet";

        query.setParameter(1, novoNome);
        query.setParameter(2, id);
        query.executeUpdate();

        Pet pet = em.find(Pet.class, id);

        assertEquals(novoNome, pet.getNome());
    }
    
    @Test /* NÃO FUNCIONA */
    public void deletaPetQuerytTeste(){
        String sql = "delete from Servico ser where ser.idServico = 3";
        String sql2 = "delete from ConsultaGeral cong where cong.servico = 3";
        
        Query query = em.createQuery(sql);
        Query query2 = em.createQuery(sql2);
        
        query2.executeUpdate();
        query.executeUpdate();
        
        Pet servico = em.find(Pet.class, 3L);
        
        assertNull(servico);
    }

}
