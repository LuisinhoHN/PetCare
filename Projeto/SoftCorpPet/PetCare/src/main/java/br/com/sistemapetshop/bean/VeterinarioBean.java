package br.com.sistemapetshop.bean;

import br.com.sistemapetshop.model.Veterinario;
import br.com.sistemapetshop.negocio.GrupoService;
import br.com.sistemapetshop.negocio.VeterinarioService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.omnifaces.util.Messages;

/**
 *
 * @author Usuario
 */
@ManagedBean(name = "veterinarioManagedBean")
@SessionScoped
public class VeterinarioBean {

    @EJB
    private VeterinarioService veterinarioService;
    
    @EJB
    private GrupoService grupoRepository;
    private Veterinario veterinario;

    @PostConstruct
    public void constroi() {
        veterinario = new Veterinario();
    }
    
    public void salvar(){
        try{
            veterinario.setGrupo(grupoRepository.getGrupo(new String[]{Veterinario.VETERINARIO}));
            veterinarioService.atualizar(veterinario);
            
            Messages.addGlobalInfo("cadastrado com sucesso!");
        }catch(Exception ex){
            
            Messages.addGlobalError("Ocorreu algum erro.");
            ex.printStackTrace(); // show error :) :(
        }
          
    }

    public void remover(){
        
    }
    
    public void atualizar(){
        
    }
    
    public void editar(){
        
    }
    
    public List<Veterinario> listarVeterinarios() {
        return veterinarioService.listar();
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

}
