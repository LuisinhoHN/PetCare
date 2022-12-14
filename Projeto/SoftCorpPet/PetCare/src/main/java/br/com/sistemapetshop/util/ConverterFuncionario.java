/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapetshop.util;

import br.com.sistemapetshop.model.Funcionario;
import br.com.sistemapetshop.negocio.FuncionarioService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Henrique
 */
@FacesConverter(value = "funcionarioConverter", forClass = Funcionario.class)
public class ConverterFuncionario implements Converter {
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            FuncionarioService service = new FuncionarioService();
            return service.buscar(Long.parseLong(string));
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && (o instanceof Funcionario)) {
            return String.valueOf(((Funcionario) o).getIdUsuario());
        }

        return null;
    }
}
