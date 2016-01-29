package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RawMeasure {

	public RawMeasure() {}
	
	public void insertMeasure(String time, String box_id, float mean, float sdeviation, float limit) {
		
		Connection con = null;
		PreparedStatement pst = null;	

		try {
			Database db_temp = new Database();
			con = db_temp.getConnection();

			pst = con.prepareStatement("INSERT INTO RawMeasure VALUES(DEFAULT, ?, ?, ?, ?, ?)");
		
			pst.setString(1, time);
			pst.setString(2, box_id);
			pst.setString(3, String.valueOf(mean));
			pst.setString(4, String.valueOf(sdeviation));
			pst.setString(5, String.valueOf(limit));

			pst.executeUpdate();

		} catch (SQLException ex) {
			System.out.println(ex);

		} finally {

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
	
	public static int getLastId() {
		Database db_temp = new Database();
		Connection con = db_temp.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		int count = 0;

		try {						
			pst = con.prepareStatement("SELECT COUNT(*) FROM RawMeasure");			
			rs = pst.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException ex) {
			System.out.println(ex);
		} finally {
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
		
		return count;
	}
}
