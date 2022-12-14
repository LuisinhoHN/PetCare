/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapetshop.bean;

import br.com.sistemapetshop.model.Servico;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jonathanpereira
 */
@ManagedBean(name = "carrinhoManagedBean")
@SessionScoped
public class CarrinhoBean {
    
    private List<Servico> servicos;
    private Servico servico;
    
    private Integer precoTotal;
    private int quantidadeServicos;
    
    private FacesContext facesContext;
    
    @PostConstruct
    public void inicializar(){
        facesContext = FacesContext.getCurrentInstance();

        listar();
    }

    public void adicionar(){
        HttpSession sessao = (HttpSession) facesContext.getExternalContext().getSession(false); 
        servicos.add(servico);
        
        sessao.setAttribute("servicos", servicos);
    }
    
    public void remover(ActionEvent evento){
        
        servico = (Servico) evento.getComponent().getAttributes().get("servicoSelecionado");
        
        for(Servico servicoAtual: servicos){
            if(servicoAtual.equals(servico)){
                servicos.remove(servicoAtual);
                break;
            }
        }
        
    }
    
    public void listar(){
        HttpSession sessao = (HttpSession) facesContext.getExternalContext().getSession(false);

        servicos = (List<Servico>) sessao.getAttribute("servicos");        
    }
    
    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Integer getPrecoTotal() {
        return precoTotal;
    }

    public int getQuantidadeServicos() {
        return quantidadeServicos;
    }

    public void setQuantidadeServicos(int quantidadeServicos) {
        this.quantidadeServicos = quantidadeServicos;
    }

    public void setPrecoTotal(Integer precoTotal) {
        this.precoTotal = precoTotal;
    }
}
