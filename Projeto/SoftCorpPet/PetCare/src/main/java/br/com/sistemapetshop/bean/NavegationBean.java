/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapetshop.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Henrique
 */
@Stateless
@ManagedBean(name = "navigationController", eager = true)
@RequestScoped
public class NavegationBean {
    FacesContext facesContext = FacesContext.getCurrentInstance();

    public String ToIndex() throws IOException {
        String link;
        link = facesContext.getExternalContext()+"index.xhtml";
        return link;

    }
}
