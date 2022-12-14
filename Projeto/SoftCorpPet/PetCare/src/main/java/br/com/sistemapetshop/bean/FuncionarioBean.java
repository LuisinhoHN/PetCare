/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapetshop.bean;

import br.com.sistemapetshop.model.ConsultaGeral;
import br.com.sistemapetshop.model.Endereco;
import br.com.sistemapetshop.model.EspecialidadeFuncionario;
import br.com.sistemapetshop.model.Funcionario;
import br.com.sistemapetshop.model.Usuario;
import br.com.sistemapetshop.model.Veterinario;
import br.com.sistemapetshop.negocio.FuncionarioService;
import br.com.sistemapetshop.negocio.GrupoService;
import br.com.sistemapetshop.negocio.VeterinarioService;
import br.com.sistemapetshop.util.WebServiceCep;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import org.omnifaces.util.Messages;

/**
 *
 * @author Usuario
 */
@ManagedBean(name = "FuncionarioManagedBean")
@SessionScoped
public class FuncionarioBean implements Serializable {

    @EJB
    private FuncionarioService funcionarioService;

    @EJB
    private VeterinarioService veterinarioService;

    @EJB
    private GrupoService grupoService;

    private Funcionario funcionario;
    private Usuario usuario;
    private Veterinario veterinario;
    private Endereco endereco;
    private List<Funcionario> listaFuncionarios;
    private EspecialidadeFuncionario especialidadeFuncionario;

    private boolean eVeterinario;
    private boolean value2;

    @PostConstruct
    public void inicializar() {
        funcionario = new Funcionario();
        endereco = new Endereco();
        usuario = new Funcionario();

        listar();
    }

    public EspecialidadeFuncionario[] getEspecialidadesFuncionario() {
        return EspecialidadeFuncionario.values();
    }

    public void constroiFuncionario() {
        funcionario = new Funcionario();
    }

    public void constroiVeterinario() {
        veterinario = new Veterinario();
    }

    public void salvar() {

        String successMsg = "Salvo com sucesso";
        String errorMsg = " Ocorreu um erro inesperado";

        try {

            if (!value2) {

                funcionario = (Funcionario) usuario;
                
                funcionario.setEndereco(endereco);
                funcionario.setGrupo(grupoService.getGrupo(new String[]{Funcionario.FUNCIONARIO}));
                funcionario.setEspecialidadeFuncionario(especialidadeFuncionario);
                funcionario.setListaConsultaGeral(new ArrayList<ConsultaGeral>());

                funcionarioService.salvar(funcionario);
            } else {
                
                veterinario.setEndereco(endereco);
                veterinario.setEmail(usuario.getEmail());
                veterinario.setLogin(usuario.getLogin());
                veterinario.setNome(usuario.getNome());
                veterinario.setSenha(usuario.getSenha());
                veterinario.setGrupo(grupoService.getGrupo(new String[]{Funcionario.VETERINARIO}));

                veterinarioService.salvar(veterinario);
            }

            Messages.addGlobalInfo(successMsg);
            listar();
        } catch (Exception ex) {

            Messages.addGlobalError(errorMsg);
            ex.printStackTrace();
        }
    }

    public void editar(ActionEvent evento) {
        funcionario = (Funcionario) evento.getComponent().getAttributes().get("funcionarioSelecionado");

        salvar();
    }

    public void remover(ActionEvent evento) {

        String successMsg = "Removido com sucesso";
        String errorMsg = " Ocorreu um erro inesperado";

        funcionario = (Funcionario) evento.getComponent().getAttributes().get("funcionarioSelecionado");

        try {

            funcionarioService.remover(funcionario);

            Messages.addGlobalInfo(successMsg);
            listar();
        } catch (Exception ex) {

            Messages.addGlobalError(errorMsg);
            ex.printStackTrace();
        }
        
        constroiFuncionario();

    }

    public void listar() {
        listaFuncionarios = funcionarioService.listar();
    }

    public void buscaCep() {

        WebServiceCep webServiceCep = WebServiceCep.searchCep(endereco.getCep());

        if (webServiceCep.wasSuccessful()) {

            endereco.setLogradouro(webServiceCep.getLogradouroFull());
            endereco.setBairro(webServiceCep.getBairro());

            Messages.addGlobalInfo("Cep encontrado!");
        } else {

            Messages.addGlobalError("Cep não encontrado");
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public EspecialidadeFuncionario getEspecialidadeFuncionario() {
        return especialidadeFuncionario;
    }

    public void setEspecialidadeFuncionario(EspecialidadeFuncionario especialidadeFuncionario) {
        this.especialidadeFuncionario = especialidadeFuncionario;
    }

    public List<Funcionario> getListaFuncionarios() {
        return listaFuncionarios;
    }

    public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
        this.listaFuncionarios = listaFuncionarios;
    }

    public boolean eVeterinario() {
        return eVeterinario;
    }

    public void seteVeterinario(boolean eVeterinario) {
        this.eVeterinario = eVeterinario;
    }

    public boolean isValue2() {
        return value2;
    }

    public void setValue2(boolean value2) {
        this.value2 = value2;
    }
}
