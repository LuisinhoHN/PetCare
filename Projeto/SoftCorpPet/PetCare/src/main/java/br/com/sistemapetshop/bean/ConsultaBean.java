/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapetshop.bean;

import br.com.sistemapetshop.model.ConsultaGeral;
import br.com.sistemapetshop.model.Funcionario;
import br.com.sistemapetshop.model.Servico;
import br.com.sistemapetshop.model.StatusConsulta;
import br.com.sistemapetshop.negocio.ConsultaGeralService;
import br.com.sistemapetshop.negocio.FuncionarioService;
import br.com.sistemapetshop.negocio.ServicoService;
import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.omnifaces.util.Messages;

/**
 *
 * @author Henrique
 */
@ManagedBean(name = "ConsultaGeralManagedBean")
@SessionScoped
public class ConsultaBean implements Serializable {

    @EJB
    private ConsultaGeralService consultaGeralService;

    private ConsultaGeral consultaGeral;
    private List<ConsultaGeral> listaConsultaGeral;
    private List<Funcionario> listaFuncionario;
    private List<Servico> listaServicos;
    private List<ConsultaGeral> listaConsultaDoUsuario;
    private StatusConsulta statusConsulta;
    private Servico servico;
    private Funcionario funcionario;
    private String dataMarcada;
    private String nomeDoServico;
    private String nomeDoFuncionario;

    
    @PostConstruct
    public void constroi() {
        //listaServicos = new List<Servico>();
        //listaFuncionario = new List<Funcionario>();
        consultaGeral = new ConsultaGeral();
        servico = new Servico();
        funcionario= new Funcionario();
        dataMarcada = new String();
        //nomeDoFuncionario = new String();
        //nomeDoServico = new String();

        listarConsultaGeral();
        listarConsultasDoUsuario();
        //listarServicos();
        //listaFuncionario = funcionarioService.listar();           
    }

    public void constroiConsultaGeral() {
        consultaGeral = new ConsultaGeral();
    }

    public void salvar() {

        
        //nomeDoServico = consultaGeral.getServico().getNome();
        //nomeDoFuncionario = consultaGeral.getFuncionario().getNome();
        
        //setServico(nomeDoServico);
        //setFuncionario(nomeDoFuncionario);
        
        consultaGeral.setServico(servico);
        consultaGeral.setFuncionario(funcionario);
        
        String defaultErrorMsg = "Ocorreu um erro inesperado";
        String defaultSuccessMsg = "Salvo com sucesso";

        try {
            
            consultaGeralService.salvar(consultaGeral);
            Messages.addGlobalInfo("Salvo com sucesso.");
            listarConsultaGeral();
        } catch (Exception exception) {
            Messages.addGlobalError(defaultErrorMsg);
            exception.printStackTrace();
        } finally {
            constroiConsultaGeral();
        }
    }

    public void editar(ActionEvent evento) {
        consultaGeral = (ConsultaGeral) evento.getComponent().getAttributes().get("consultaSelecionada");
    }

    public void excluir(ActionEvent evento) {

        String defaultSuccessMsg = "Consulta removida com sucesso";
        String defaultErrorMsg = "Erro ao excluir a consulta";

        //Qual foi o componente clicado? Qual são os atributos? Qual o nome do atributo que eu quero trabalhar?
        consultaGeral = (ConsultaGeral) evento.getComponent().getAttributes().get("servicoSelecionado");

        try {
            System.out.println("CONSULTA: " + consultaGeral.getServico()+ "DATA: "+ consultaGeral.getDataMarcada());
            consultaGeralService.remover(consultaGeral);

            Messages.addGlobalInfo(defaultSuccessMsg);
            constroi();

        } catch (Exception erro) {
            Messages.addGlobalError(defaultErrorMsg);
            erro.printStackTrace();
        }
//        } finally {
//            constroiServico();
//        }
    }
    
    public void listarConsultasDoUsuario(){
        String errorMsg = "Erro ao carregar a lista";
        try {
            listaConsultaGeral = consultaGeralService.listar();
            String nomeUsuario =  FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
            for(int i=0; i<listaConsultaGeral.size();i++){
                if(listaConsultaDoUsuario.get(i).getFuncionario().getNome() == nomeUsuario){
                    listaConsultaDoUsuario.add(listaConsultaGeral.get(i));
                }
            }
            
        } catch (Exception ex) {
            Messages.addGlobalError(errorMsg);
        }
    
    }

    public void listarConsultaGeral() {

        String errorMsg = "Erro ao carregar a lista";

        try {
            listaConsultaGeral = consultaGeralService.listar();
        } catch (Exception ex) {
            Messages.addGlobalError(errorMsg);
        }
    }
    
    public StatusConsulta[] getStatusConsultas(){
        return StatusConsulta.values();
    }
    
    public StatusConsulta getStatusConsulta(){
        return statusConsulta;
    }
    
    public void setStatusConsulta(StatusConsulta statusConsulta){
        this.statusConsulta = statusConsulta;
    }

    public List<ConsultaGeral> getListaConsultaGeral() {
        return listaConsultaGeral;
    }

    public void setListaConsultaGeral(List<ConsultaGeral> listaConsultaGeral) {
        this.listaConsultaGeral = listaConsultaGeral;
    }
    
    public List<ConsultaGeral> getListaConsultaDoUsuario() {
        return listaConsultaDoUsuario;
    }

    public void setListaConsultaDoUsuario(List<ConsultaGeral> listaConsulta) {
        this.listaConsultaDoUsuario = listaConsulta;
    }

    public ConsultaGeral getConsultaGeral() {
        return consultaGeral;
    }
    
    public Servico getServico(){
        return servico;
    }
    
    public void setServico(Servico novo){
        servico = novo;
    }
    
    /*
    public String getNomeDoServico(){
        return nomeDoServico;
    }
    
    public void setNomeDoServico(String nome){
        nomeDoServico = nome;
    }
    
    
    public String getNomeDoFuncionario(){
        return nomeDoFuncionario;
    }
    
    public void setNomeDoFuncionario(String nome){
        nomeDoFuncionario = nome;
    }
    */
    
    public void setDataMarcada(String dataMarcada) throws ParseException{
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        Date date = (Date)formatter.parse(dataMarcada);
        consultaGeral.setDataMarcada(date);
    }
    
    /*
    public void setFuncionario(String nomeFuncionario){
        for(int i=0; i<listaFuncionario.size();i++){
            if(nomeFuncionario == listaFuncionario.get(i).getNome()){
                funcionario = listaFuncionario.get(i);
            }
        }
        consultaGeral.setFuncionario(funcionario);
    }
    
    public void setServico(String nomeServico){
        //listaServicos = servicoService.listar();
        for(int i=0; i<listaServicos.size();i++){
            if(nomeServico == listaServicos.get(i).getNome()){
                servico = listaServicos.get(i);
            }
        }
        consultaGeral.setServico(servico);
    }*/
    
}

