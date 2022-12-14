/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapetshop.interceptadores;

import br.com.sistemapetshop.acesso.Papel;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import org.omnifaces.util.Messages;

/**
 *
 * @author MASC
 */
@Interceptor
public class LoginInterceptador {

    @Resource
    private SessionContext contexto;

    private HttpServletRequest getHttpServletRequest(InvocationContext ic) {
        HttpServletRequest request = null;
        for (Object parameter : ic.getParameters()) {
            if (parameter instanceof HttpServletRequest) {
                request = (HttpServletRequest) parameter;
            }
        }

        return request;
    }

    private HttpHeaders getHttpHeaders(InvocationContext ic) {
        HttpHeaders headers = null;

        for (Object parameter : ic.getParameters()) {
            if (parameter instanceof HttpHeaders) {
                headers = (HttpHeaders) parameter;
            }
        }

        return headers;
    }

    private boolean valido(String valor) {
        return valor != null && valor.trim().length() > 0;
    }

    @AroundInvoke
    public Object interceptar(InvocationContext ic) throws Exception {
        Object resultado = null;
        HttpServletRequest request = null;
        HttpHeaders headers;

        try {
            request = getHttpServletRequest(ic);
            headers = getHttpHeaders(ic);

            String autorizacao = headers.getHeaderString("Authorization");
            String autorizacaoArray[] = new String[]{null, null};

            if (autorizacao != null && autorizacao.length() > 6) {
                autorizacao = autorizacao.substring(6);
                byte[] bytes = Base64.getDecoder().decode(autorizacao);
                String utf8 = new String(bytes, StandardCharsets.UTF_8);
                if (utf8.contains(":")) {
                    autorizacaoArray = utf8.split(":");
                }
            }

            String login = autorizacaoArray[0];
            String senha = autorizacaoArray[1];

            if (valido(login) && valido(senha)) {
                try {
                    request.login(login, senha);
                    request.getSession(true);

                    if (contexto.isCallerInRole(Papel.ADMINISTRADOR)) {
                        resultado = ic.proceed();
                    } else {
                        Messages.addGlobalError("Acesso não autorizado");
                    }

                } catch (ServletException ex) {
                    Messages.addGlobalError("Login ou senha inválidos");
                }
            } else {
                Messages.addGlobalError("Informe Login e Senha");

            }
        } finally {
            if (request != null) {
                if (request.getSession(false) != null) {
                    request.getSession(false).invalidate();
                }

                request.logout();
            }
        }

        return resultado;
    }

}
