/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package handlers;

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.derby.client.am.PreparedStatement;

/**
 *
 * @author jahf
 */

public class dbHandler {

    private String dbUsername = "veriSec";
    private String dbPass = "veriSec";
    private Connection con = null;
    
    public dbHandler(){}
    
    
    public void connectDB() {
        Connection connectionAux = null;

        String driver = "org.apache.derby.jdbc.ClientDriver";
        try {
            Class.forName(driver);
        } catch (java.lang.ClassNotFoundException e) {
        }
        try {
            connectionAux = DriverManager.getConnection("jdbc:derby://localhost:1527/veriSec", this.dbUsername, this.dbPass);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Nao foi possivel ligar a base de dados! (desligada?)");
        }

        System.out.println("Ligação à BD OK");
       this.con = connectionAux;
    }

    public void disconnectDB(){
        try {
            this.con.close();
            this.con = null;
        } catch (SQLException ex) {
            Logger.getLogger(dbHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

      public void registUser(X509Certificate cert, int bi, String nome) {

        if(this.con == null){ this.connectDB(); }
        String certB64 = null;
        try {
            certB64 = Base64.encodeToString(cert.getEncoded(), true);
        } catch (CertificateEncodingException ex) {
            System.out.println("ERROR Getting encoded certificate..");
        }

        String query = "INSERT INTO APP.USERDATA VALUES ( ? , ? , ? )";

        PreparedStatement st = null;
        try {
           st = (PreparedStatement) con.prepareStatement(query);
           st.setInt(1, bi);
           st.setString(2, nome);
           st.setString(3, certB64);
           st.execute();
           this.con.commit();

            System.out.println("[OK] - User Stored in BD..");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Verificar se certificado não se encontra já na Base de dados");
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println("ERROR"+ex.toString());
            }
        }

    }

}

