package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Incident {

	int raw_measure_id;
	int processed_measure_id;

	public Incident(int id) {
		raw_measure_id = id;
	}

	public void saveRawIncident() {

		Connection con = null;
		PreparedStatement pst = null;

		try {
			Database db_temp = new Database();
			con = db_temp.getConnection();

			pst = con.prepareStatement("INSERT INTO Incident(id_incident, id_rawmeasure) VALUES(DEFAULT, ?)");

			pst.setInt(1, raw_measure_id);
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
}
