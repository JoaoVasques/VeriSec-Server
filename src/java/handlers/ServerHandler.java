/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 *
 * @author joaovasques
 */
public class ServerHandler implements SOAPHandler<SOAPMessageContext> {
    
    public boolean handleMessage(SOAPMessageContext messageContext) {
        
        String outputlogFile = "/Users/joaovasques/Desktop/Outaiaclog.txt";
        String intputlogFile = "Users/joaovasques/Desktop/Inaiaclog.txt";
        
        File outputLog = new File(outputlogFile);
        File inputLog = new File(intputlogFile);
        
        if(!outputLog.exists())
            try {
            outputLog.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(!inputLog.exists())
        {
            try {
                inputLog.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        FileOutputStream outOS = null;
        try {
           outOS  = new FileOutputStream(outputLog);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FileOutputStream inOS = null;
        try {
            inOS = new FileOutputStream(inputLog);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        Boolean outcoming = (Boolean)messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if(outcoming.booleanValue())
        {
            try {
                log(messageContext, outOS);
            } catch (IOException ex) {
                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        /*else
        {
            try {
                log(messageContext,inOS);
            } catch (IOException ex) {
                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
         
        return true;
    }
    
    
    
    public Set<QName> getHeaders() {
        return Collections.EMPTY_SET;
    }
    
    public boolean handleFault(SOAPMessageContext messageContext) {
        return true;
    }
    
    public void close(MessageContext context) {
    }
    
    private void log(SOAPMessageContext msg, FileOutputStream log) throws IOException
    {
        Boolean outcoming = (Boolean)msg.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
                     
        log.write("\n\n".getBytes());
        
        if(msg == null)
        {
            log.write("Message is Null".getBytes());
            log.write(("arriving message: " + outcoming.booleanValue()).getBytes());
            return;
        }
           
        SOAPMessage message = msg.getMessage();
  
        
        try {
            message.writeTo(log);
        } catch (SOAPException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if(message.getSOAPHeader() == null)
                log.write("\nheader EMPY!!!".getBytes());
        } catch (SOAPException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
