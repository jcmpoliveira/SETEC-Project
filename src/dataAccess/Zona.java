package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dataAccess.Database;

public class Zona {
	int id;
	String tipo;
	float lat11, lat12, lat21, lat22;
	float longi11, longi12, longi21, longi22;

	public Zona() {
	}

	public Zona(int id, String tipo, float lat11, float longi11, float lat12, float longi12, float lat21, float longi21, float lat22, float longi22) {
		this.id = id;
		this.tipo = tipo;
		this.lat11 = lat11;
		this.lat12 = lat12;
		this.lat21 = lat21;
		this.lat22 = lat22;
		this.longi11 = longi11;
		this.longi12 = longi12;
		this.longi21 = longi21;
		this.longi22 = longi22;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setId(String tipo) {
		this.tipo = tipo;
	}

	public float getLat11() {
		return lat11;
	}

	public void setLat11(float lat11) {
		this.lat11 = lat11;
	}

	public float getLat12() {
		return lat12;
	}

	public void setLat12(float lat12) {
		this.lat12 = lat12;
	}

	public float getLat21() {
		return lat21;
	}

	public void setLat21(float lat21) {
		this.lat21 = lat21;
	}

	public float getLat22() {
		return lat22;
	}

	public void setLat22(float lat22) {
		this.lat22 = lat22;
	}

	public float getLongi11() {
		return longi11;
	}

	public void setLongi11(float longi11) {
		this.longi11 = longi11;
	}

	public float getLongi12() {
		return longi12;
	}

	public void setLongi12(float longi12) {
		this.longi12 = longi12;
	}

	public float getLongi21() {
		return longi21;
	}

	public void setLongi21(float longi21) {
		this.longi21 = longi21;
	}

	public float getLongi22() {
		return longi22;
	}

	public void setLongi22(float longi22) {
		this.longi22 = longi22;
	}

	public static int verifyZone(float lat, float longi) {
		int id_zone = 0;
		
		ArrayList<Zona> zonas = getZonas();
		System.out.println("Latitude = " + String.valueOf(lat) + " Longitude = " + String.valueOf(longi));

		for (int i = 0; i < zonas.size(); i++) {
			if ((zonas.get(i).lat11 >= lat) && (lat >= zonas.get(i).lat21) && (zonas.get(i).longi22 <= longi) && (longi <= zonas.get(i).longi21)) {
				id_zone = zonas.get(i).id;
				break;
			} 
			else {
				System.out.println("Zone not found");
			}
		}
		return id_zone;

	}

	public static Zona getZoneFromId(int zone_id) {
		Database db_temp = new Database();
		Connection con = db_temp.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		Zona result = null;

		try {						
			pst = con.prepareStatement("SELECT * FROM Zone WHERE id_zone = ?");
			pst.setInt(1, zone_id);
			rs = pst.executeQuery();

			if (rs.next()) {
				result = new Zona(rs.getInt(1), rs.getString(2),
						rs.getFloat(3), rs.getFloat(4), rs.getFloat(5),
						rs.getFloat(6), rs.getFloat(7), rs.getFloat(8),
						rs.getFloat(9), rs.getFloat(10));
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
		
		return result;
	}

	public static ArrayList<Zona> getZonas() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Database db_temp = new Database();
		Connection con = db_temp.getConnection();
		ArrayList<Zona> zonas = new ArrayList<Zona>();

		try {
			pst = con.prepareStatement("SELECT id_zone from Zone");
			rs = pst.executeQuery();
			while (rs.next()) {
				zonas.add(Zona.getZoneFromId(rs.getInt(1)));
			}
		}

		catch (SQLException ex) {
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

		return zonas;

	}
}
