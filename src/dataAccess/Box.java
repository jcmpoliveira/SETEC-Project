package dataAccess;

import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.xml.bind.annotation.XmlRootElement;

import dataAccess.Database;

@XmlRootElement
public class Box 
{
    String id;
    float coordinatesLat;
    float coordinatesLong;
    String status;
    
    
    public Box() {}
    
    public Box(String id, String status, float coordinatesLat, float coordinatesLong) {
        this.id = id;
        this.coordinatesLat = coordinatesLat;
        this.coordinatesLong = coordinatesLong;
        this.status= status;
    }

    public String getId() 
    {
        return id;
    }

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }
    
    public float getCoordinatesLat() 
    {
        return coordinatesLat;
    }

    public void setCoordinatesLat(float coordinatesLat) 
    {
        this.coordinatesLat = coordinatesLat;
    }

    public float getCoordinatesLong() 
    {
        return coordinatesLong;
    }

    public void setCoordinatesLong(float coordinatesLong) 
    {
        this.coordinatesLong = coordinatesLong;
    }
    
    public static Box getBoxFromId(String mbox_id)
    {
    	Database db_temp = new Database(); 	
    	Connection con = db_temp.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Box result = null;
        
    	try 
    	{
            pst = con.prepareStatement("SELECT Box.id_box, Box.status, Address.latitude, Address.longitude FROM Box JOIN Address WHERE id_box = ? AND Box.id_address = Address.id_address;");
            pst.setString(1, mbox_id);
            rs = pst.executeQuery();
            if(rs.next() == true) {  //THEN THE BOX DOES EXIST            	
            	result = new Box(rs.getString(1), rs.getString(2), Float.parseFloat(rs.getString(3)), Float.parseFloat(rs.getString(4)));
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
    
    public static List<Box> getAllBoxes() {
    	Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Box> boxes = new ArrayList<Box>();
                
    	try {
    		Database db_temp = new Database(); 	
        	con = db_temp.getConnection();
            pst = con.prepareStatement("SELECT id_box from Box");
            rs = pst.executeQuery();
            ArrayList<String> ids = new ArrayList<String>();
            while (rs.next()) {
                  ids.add(rs.getString(1));
            }
            for (int i=0; i<ids.size(); i++)  {
            	boxes.add(Box.getBoxFromId(ids.get(i)));
            }
        } catch (SQLException ex) {
        	System.out.println(ex);        	
        }     	
    	finally {
            try {
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

            } catch (SQLException ex) {
            	System.out.println(ex);
            }
        }    	
    	return boxes;
    }
    
    public void insertBox(){
		
    	Connection con = null;
        PreparedStatement pst = null;            
        int id_endereco;
        
    	try {
    		Database db_temp = new Database(); 	
        	con = db_temp.getConnection();
        	
        	pst = con.prepareStatement("INSERT INTO Box(id_box, status, id_address) VALUES(?, ?, ?)");        		
        	pst.setString(1, id);
        	pst.setString(2, status);
    		
        	Address novo_end = new Address(coordinatesLat, coordinatesLong);
    		id_endereco = novo_end.getIdFromCoordinates();
    		if(id_endereco == 0) { 
    			novo_end.insertAddress(); 
    			id_endereco = novo_end.getIdFromCoordinates();
    		}     
    		
    		pst.setInt(3, id_endereco);
            pst.executeUpdate();                
           
        }         	
    	catch (SQLException ex)	{
            System.out.println(ex);

    	}         	
    	finally {

            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
            	System.out.println(ex);
            }
        }
    }
    
    public void updateBox(){
    	Connection con = null;
        PreparedStatement pst = null;
        int id_endereco;
        
        try {
        	Database db_temp = new Database(); 	
        	con = db_temp.getConnection();
        	pst = con.prepareStatement("UPDATE Box SET status = ?, id_address = ? WHERE id_box = ?");
        	
        	pst.setString(1, status);
        	Address novo_end = new Address(coordinatesLat, coordinatesLong);
    		id_endereco = novo_end.getIdFromCoordinates();
    		if(id_endereco == 0) { 
    			novo_end.insertAddress(); 
    			id_endereco = novo_end.getIdFromCoordinates();
    		}
            pst.setInt(2, id_endereco);
            pst.setString(3, id);
            pst.executeUpdate();
            
        } catch (SQLException ex)	{
            System.out.println(ex);
    	}         	
    	finally {

            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
            	System.out.println(ex);
            }
        }
    }
}
