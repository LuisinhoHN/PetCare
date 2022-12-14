/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapetshop.util;

import br.com.sistemapetshop.model.Servico;
import br.com.sistemapetshop.negocio.ServicoService;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Henrique
 */
@FacesConverter(value = "servicoConverter", forClass = Servico.class)
public class ConverterServico implements Converter{

    @EJB
    private ServicoService service;
    
    private Servico servico= new Servico();
            
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Long id = Long.parseLong(value);
            servico = service.buscar(id);
            return servico;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value != null && (value instanceof Servico)) {
            String valor = new String();
            valor = String.valueOf(((Servico) value).getIdServico());
            return valor;
        }

        return null;
    }
    
    
}
