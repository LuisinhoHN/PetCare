/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapetshop.util;

import javax.faces.context.FacesContext;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;

/**
 *
 * @author Jonathan Romualdo
 */
public class Recaptcha {

    private String recaptchaResponse;
    private String secretKey;
    private String url;

    /**
     *
     * @param facesContext
     */
    public Recaptcha(FacesContext facesContext) {
        
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        
        this.recaptchaResponse = request.getParameter("g-recaptcha-response");
        this.secretKey = facesContext.getExternalContext().getInitParameter("PRIVATE_CAPTCHA_KEY");
        this.url = facesContext.getExternalContext().getInitParameter("CAPTCHA_URL");
    }

    public boolean validar() {
        
        if (recaptchaResponse == null || "".equals(recaptchaResponse)) {
            return false;
        }

        Client client = ClientBuilder.newClient();
        
        WebTarget webTarget = client.target(url);
        webTarget = webTarget.path("api");
        webTarget = webTarget.path("siteverify");
        
        Form form = new Form();
        form = form.param("secret", secretKey);
        form = form.param("response", recaptchaResponse);
        
        JsonObject jsonObject = webTarget.request().post(Entity.form(form), JsonObject.class);
        
        return jsonObject.getBoolean("success");
    }
}
