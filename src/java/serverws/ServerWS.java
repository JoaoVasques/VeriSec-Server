/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serverws;

import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;

/**
 *
 * @author joaovasques
 */
@WebService(serviceName = "ServerWS")
@Stateless()
//@HandlerChain(file = "ServerWS_handler.xml")
public class ServerWS {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "Hello")
    public String Hello() {

        String hello = "Hello World";
        return hello;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "Echo")
    public String Echo(@WebParam(name = "message")
    String message) {
        return "Echo " + message;
    }
}
