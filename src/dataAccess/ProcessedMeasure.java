package dataAccess;

import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProcessedMeasure {
	
	private int id;
	private String timestamp;
	private float measure;
	private float latitude;
	private float longitude;
	private float radius;
	private boolean type;
	private float decay;
	private boolean incident;
	private int permit_id;
	private String box_id;
	
	public ProcessedMeasure() {
	}
	
	public ProcessedMeasure(int id, String timestamp, float measure, float latitude, float longitude, float radius, boolean type, float decay, boolean incident, int permit, String box) {
		this.id = id;
		this.timestamp = timestamp;
		this.measure = measure;
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		this.type = type;
		this.decay = decay;
		this.incident = incident;
		this.permit_id = permit;
		this.box_id = box;
	}

	public static ProcessedMeasure getStoredMeasureFromId(int measure_id) {
    	Database db_temp = new Database(); 	
    	Connection con = db_temp.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;
        ProcessedMeasure result = null;
        
    	try {
            pst = con.prepareStatement("SELECT * FROM Measurement WHERE measure_id = ?");
            pst.setInt(1, measure_id);
            rs = pst.executeQuery();
            if(rs.next() == true) {            	
            	result = new ProcessedMeasure(rs.getInt(1), rs.getString(2), Float.valueOf(rs.getString(3)), Float.valueOf(rs.getString(4)), Float.valueOf(rs.getString(5)),
										 Float.valueOf(rs.getString(6)), Boolean.valueOf(rs.getString(7)), Float.valueOf(rs.getString(8)), Boolean.valueOf(rs.getString(9)),
										 rs.getInt(10), rs.getString(11));
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
	
	public static ArrayList<ProcessedMeasure> getMeasurementsFromBox(String box_id) {
    	Database db_temp = new Database(); 	
    	Connection con = db_temp.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;        
        ArrayList<ProcessedMeasure> measures = new ArrayList<>();
		
    	try {
            pst = con.prepareStatement("SELECT * FROM Measurement WHERE box = ?");
            pst.setString(1, box_id);
            rs = pst.executeQuery();
            while(rs.next() == true) {            	
            	ProcessedMeasure measure = new ProcessedMeasure
										(rs.getInt(1), rs.getString(2), Float.valueOf(rs.getString(3)), Float.valueOf(rs.getString(4)), Float.valueOf(rs.getString(5)), Float.valueOf(rs.getString(6)), Boolean.valueOf(rs.getString(7)), Float.valueOf(rs.getString(8)), Boolean.valueOf(rs.getString(9)),
										rs.getInt(10), rs.getString(11));
				measures.add(measure);						
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
    	return measures;  
    }

	public void insertMeasurement() {
		Connection con = null;
        PreparedStatement pst = null;            
        
    	try {
    		Database db_temp = new Database(); 	
        	con = db_temp.getConnection();
        	
        	pst = con.prepareStatement("INSERT INTO Measurement VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");        		
        	pst.setInt(1, id);
        	pst.setString(2, timestamp);
			pst.setString(3, String.valueOf(measure));
    		pst.setString(4, String.valueOf(latitude));
			pst.setString(5, String.valueOf(longitude));
			pst.setString(6, String.valueOf(radius));
			pst.setString(7, String.valueOf(type));
			pst.setString(8, String.valueOf(decay));
			pst.setString(9, String.valueOf(incident));
			pst.setString(10, String.valueOf(permit_id));
			pst.setString(11, box_id);

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