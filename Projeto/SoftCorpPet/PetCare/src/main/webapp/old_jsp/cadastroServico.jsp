<%-- 
    Document   : cadastroServico
    Created on : 03/06/2017, 09:14:33
    Author     : Usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro de serviços</title>
    </head>
    <body>
        <h1>Cadastre um serviço para o Pet shop!</h1>
        
        <form action="CadastroServico" method="POST">
            <div>
                <label>Nome do serviço:</label>
                <input type="text" name="nome" id="nome" placeholder="Banho" value="Novo banho"/>
            </div>
            
            <div>
                <label>Valor dos serviço:</label>
                <input type="number" name="valor" id="valor" placeholder="200.00" value="200"/>
            </div>
            
            <button type="submit">Cadastrar</button>
        </form>
    </body>
</html>
