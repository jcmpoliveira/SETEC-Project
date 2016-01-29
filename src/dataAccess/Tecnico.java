package dataAccess;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.xml.bind.annotation.XmlRootElement;

import dataAccess.Database;


@XmlRootElement
public class Tecnico {
	int id_tecnico;
	String codigo;
	Database db;
	
	public Tecnico() {}
	
	public Tecnico(int id){		
		id_tecnico=id;		
		db = new Database();
	}
	
	public Tecnico(String code){		
		codigo = code.toLowerCase();	
		db = new Database();
	}
	
	public int getId() {
        return id_tecnico;
    }
    
    public String getCode() {
        return codigo;
    }

    public String selectCodigoTable() {
    	Connection con = (Connection) db.getConnection();
    	PreparedStatement pst = null;
    	ResultSet userTuple = null;
    	String code;
    	
    	try {
            pst = con.prepareStatement("SELECT codigo FROM Tecnico where codigo = ?");
            pst.setString(1, codigo);
            userTuple = pst.executeQuery();
            
            if(userTuple.next()) {
            	code = userTuple.getString(1);
            }
            else {
            	code = "NOT_FOUND";
            }
        } catch (SQLException ex) {            
            System.out.println("ERRO getting user row: "+ex);
            code = "NOT_FOUND";
        } finally {
        	try {
              	if (pst != null) {
              		pst.close();
                }
              	if (con != null) {
              		con.close();
                }								
			} catch (SQLException e) {		
				e.printStackTrace();
			}
        }                    
		return code;       			
    }
}
