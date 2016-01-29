package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Address {
	private int id_address;
	private int id_zone;
	private float latitude;
	private float longitude;
	Database db;
	
	public Address(int id_add) {
		id_address = id_add;
		db = new Database();
	}
	
	public Address(float lati, float longi) {
		latitude = lati;
		longitude = longi;
		new Zona();
		id_zone = Zona.verifyZone(lati, longi);
		db = new Database();
	}
	
	public Address(int id_add, int id_zona, float lati, float longi) {
		id_address = id_add;
		id_zone = id_zona;
		latitude = lati;
		longitude = longi;
		db = new Database();
	}
	
	public float getLatitude() {
		return latitude;
	}
	
	public float getLongitude() {
		return longitude;
	}
	
	public int getIdAddress() {
		return id_address;
	}
	
	public int getIdZone() {
		return id_zone;
	}
	
	public Address getAddressFromId() {

		Connection con = (Connection) db.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Address result = null;
        
    	try 
    	{            
            pst = con.prepareStatement("SELECT * FROM Address WHERE id_address = ?");
            pst.setInt(1, id_address);
            rs = pst.executeQuery();
            if(rs.next() == true) {  //THEN THE Address DOES EXIST            	
            	result = new Address(rs.getInt(1), rs.getInt(2), Float.parseFloat(rs.getString(3)), Float.parseFloat(rs.getString(4)));
            }

        }    	
    	catch (SQLException ex) 
    	{
    		System.out.println(ex);
        } 
    	
    	finally 
    	{

            try 
            {
                if (rs != null) 
                {
                    rs.close();
                }
                if (pst != null) 
                {
                    pst.close();
                }
                if (con != null) 
                {
                    con.close();
                }
            }
            
            catch (SQLException ex) 
            {
            	System.out.println(ex);
            }
        }
    	return result;    
	}
	
	public void insertAddress() {
		Connection con = (Connection) db.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        
    	try 
    	{            
            pst = con.prepareStatement("INSERT INTO Address values (DEFAULT, ?, ?, ?)");
            pst.setInt(1, id_zone);
            pst.setString(2, String.valueOf(latitude));
            pst.setString(3, String.valueOf(longitude));
            pst.executeUpdate();

        }    	
    	catch (SQLException ex) 
    	{
    		System.out.println(ex);
        } 
    	
    	finally 
    	{

            try 
            {
                if (rs != null) 
                {
                    rs.close();
                }
                if (pst != null) 
                {
                    pst.close();
                }
                if (con != null) 
                {
                    con.close();
                }
            }
            
            catch (SQLException ex) 
            {
            	System.out.println(ex);
            }
        }   		
	}

	public int getIdFromCoordinates() {
		Connection con = (Connection) db.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        int result = 0;
        
    	try 
    	{            
            pst = con.prepareStatement("SELECT * FROM Address WHERE latitude = ? AND longitude = ?");
            pst.setFloat(1, latitude);
            pst.setFloat(2, longitude);
            rs = pst.executeQuery();
            if(rs.next() == true) {  //THEN THE Address DOES EXIST            	
            	result = rs.getInt(1);
            }

        }    	
    	catch (SQLException ex) 
    	{
    		System.out.println(ex);
        } 
    	
    	finally 
    	{

            try 
            {
                if (rs != null) 
                {
                    rs.close();
                }
                if (pst != null) 
                {
                    pst.close();
                }
                if (con != null) 
                {
                    con.close();
                }
            }
            
            catch (SQLException ex) 
            {
            	System.out.println(ex);
            }
        }
    	return result;
	}
	
}
