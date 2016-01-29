package dataAccess;

import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Permit {
	
	@XmlElement(name = "code")
	int code;
	@XmlElement(name = "begin_time")
	String begin_time; 
	@XmlElement(name = "end_time")
	String end_time;
	@XmlElement(name = "begin_date")
	String begin_date;
	@XmlElement(name = "end_date")
	String end_date;
	@XmlElement(name = "type")
	String type;
	@XmlElement(name = "lat1")
	float lat1;
	@XmlElement(name = "lat2")
	float lat2;
	@XmlElement(name = "lat3")
	float lat3;
	@XmlElement(name = "lat4")
	float lat4;			
	@XmlElement(name = "long1")
	float long1;
	@XmlElement(name = "long2")
	float long2;
	@XmlElement(name = "long3")
	float long3;
	@XmlElement(name = "long4")
	float long4;
	@XmlElement(name = "max_db")
	int max_db;
	
	public Permit() {
	}
	
	public Permit(int id) {
		code = id;
	}
	
	public Permit(int code, String begin_time, String end_time, String begin_date, String end_date, String type, float lat1, float lat2, float lat3, float lat4, float long1, float long2, float long3, float long4, int max_db) {
		this.code = code;
		this.begin_time = begin_time;
		this.end_time = end_time;
		this.begin_date = begin_date;
		this.end_date = end_date;
		this.type = type;
		this.lat1 = lat1;
		this.lat2 = lat2;
		this.lat3 = lat3;
		this.lat4 = lat4;
		this.long1 = long1;
		this.long2 = long2;
		this.long3 = long3;
		this.long4 = long4;
		this.max_db = max_db;
		}

	public int getId() {
		return code;
	}
	
	public String getBegin_time()
	{
		return begin_time;
	}
	
	public String getEnd_time()
	{
		return end_time;
	}
	
	public String getBegin_date()
	{
		return begin_date;
	}
	
	public String getEnd_date()
	{
		return end_date;
	}
	
	public float getLat1()
	{
		return lat1;
	}
	
	public float getLat2()
	{
		return lat2;
	}
	
	public float getLat3()
	{
		return lat3;
	}
	
	public float getLat4()
	{
		return lat4;
	}
	
	public String getType() {
		return type;
	}
	
	public float getLong1()
	{
		return long1;
	}
	
	public float getLong2()
	{
		return long2;
	}
	
	public float getLong3()
	{
		return long3;
	}
	
	public float getLong4()
	{
		return long4;
	}
	
	
	public int getMaxDB()
	{
		return max_db;
	}
	
	
	public static Permit getPermitFromId(int permit_id) {
    	Database db_temp = new Database(); 	
    	Connection con = db_temp.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        Permit result = null;
        
    	try {
            pst = con.prepareStatement("SELECT * FROM Permit WHERE id_permit = ?");
            pst.setInt(1, permit_id);
            rs = pst.executeQuery();
            if(rs.next() == true) {            	            	
            	result = new Permit(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), 
            			Float.valueOf(rs.getString(7)), Float.valueOf(rs.getString(7)), Float.valueOf(rs.getString(8)),
						Float.valueOf(rs.getString(8)), Float.valueOf(rs.getString(9)), Float.valueOf(rs.getString(10)), 
						Float.valueOf(rs.getString(9)), Float.valueOf(rs.getString(10)), rs.getInt(11));
            }
        }    	
    	catch (SQLException ex) {
    		System.out.println(ex);
        }    	
    	finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            }            
            catch (SQLException ex) {
            	System.out.println(ex);
            }
        }
    	
    	return result;  
    }

	public void insertPermit() {
		Connection con = null;
        PreparedStatement pst = null;            
        
    	try {
    		Database db_temp = new Database(); 	
        	con = db_temp.getConnection();
        	
        	pst = con.prepareStatement("INSERT INTO Permit VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");        		
        	pst.setString(1, begin_time);
			pst.setString(2, end_time);
    		pst.setString(3, begin_date);
			pst.setString(4, end_date);			
			pst.setString(5, type);
			pst.setString(6, String.valueOf(lat1));
			pst.setString(7, String.valueOf(lat4));
			pst.setString(8, String.valueOf(long1));
			pst.setString(9, String.valueOf(long2));			
			pst.setInt(10, max_db);

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
	
	public static List<Permit> getAllPermits() {
    	Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Permit> permits = new ArrayList<Permit>();
                
    	try {
    		Database db_temp = new Database(); 	
        	con = db_temp.getConnection();
            pst = con.prepareStatement("SELECT id_permit from Permit");
            rs = pst.executeQuery();
            ArrayList<Integer> ids = new ArrayList<Integer>();
            while (rs.next()) {
                  ids.add(rs.getInt(1));
            }
            for (int i=0; i<ids.size(); i++)  {
            	permits.add(Permit.getPermitFromId(ids.get(i)));
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
    	return permits;
    }
	
	public static void deletePermit(int id) {
		Connection con = null;
        PreparedStatement pst = null;            
        
    	try {
    		Database db_temp = new Database(); 	
        	con = db_temp.getConnection();
        	
        	pst = con.prepareStatement("DELETE FROM Permit WHERE id_permit = ?");        		
        	pst.setInt(1, id);
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
	
	public void updatePermit() {
		Connection con = null;
        PreparedStatement pst = null;            
        
    	try {
    		Database db_temp = new Database(); 	
        	con = db_temp.getConnection();
        	
        	pst = con.prepareStatement("UPDATE Permit SET time_begin = ?, time_end = ?, date_begin = ?, date_end = ?, type = ?, lat1 = ?, lat4 = ?, long1 = ?, long2 = ?, maxdb = ? WHERE id_permit = ?");        		
        	pst.setString(1, begin_time);
			pst.setString(2, end_time);
    		pst.setString(3, begin_date);
			pst.setString(4, end_date);			
			pst.setString(5, type);
			pst.setString(6, String.valueOf(lat1));
			pst.setString(7, String.valueOf(lat4));
			pst.setString(8, String.valueOf(long1));
			pst.setString(9, String.valueOf(long2));			
			pst.setInt(10, max_db);
			pst.setInt(11, code);
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
	
}