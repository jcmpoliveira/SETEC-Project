package measuresProcessing;

import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import dataAccess.Box;
import dataAccess.Incident;
import dataAccess.Permit;
import dataAccess.RawMeasure;
import dataAccess.Zona;

@XmlRootElement
public class Measurement extends Thread {

	String mBoxId;
	String measurementTimestamp;
	@XmlElement(name = "values")
	float[] values;

	public Measurement() {
	}

	public Measurement(String mBoxId, String measurementTimestamp,
			float[] values) {
		this.mBoxId = mBoxId;
		this.measurementTimestamp = measurementTimestamp;
		this.values = values;
	}

	public String getmBoxId() {
		return mBoxId;
	}

	public void setmBoxId(String mBoxId) {
		this.mBoxId = mBoxId;
	}

	public String getMeasurementTimestamp() {
		return measurementTimestamp;
	}

	public void setMeasurementTimestamp(String measurementTimestamp) {
		this.measurementTimestamp = measurementTimestamp;
	}

	public float[] getValues() {
		return values;
	}

	public void setvalues(float[] values) {
		this.values = values;
	}

	public void run() {
		float average, sdeviation;
		float toCompare, limite;
		int measure_id;

		average = calculateAverage();
		sdeviation = calculateSDeviation(average);
		toCompare = average + 2 * sdeviation;
		limite = calculateLimite(mBoxId, measurementTimestamp);

		storeMeasure(mBoxId, measurementTimestamp, average, sdeviation, limite);
		measure_id = RawMeasure.getLastId();
		if (toCompare > limite) {
			Incident incident = new Incident(measure_id);
			incident.saveRawIncident();
		}		
	}

	private void storeMeasure(String box_id, String timestamp, float average, float sdeviation, float limit) {
		RawMeasure measures = new RawMeasure();
		measures.insertMeasure(timestamp, box_id, average, sdeviation, limit);
	}

	private float calculateLimite(String box_id, String measure_timestamp) {
		LegalLimitsMap limits = new LegalLimitsMap();
		int area, noise_indicator;
		float limit;

		limit = checkForPermit(box_id, measure_timestamp); //is there a permit?
		if (limit == 0) { // No permit
			area = checkZone(box_id);
			noise_indicator = checkIndicator(measure_timestamp);
			limit = limits.getLimit(area, noise_indicator);
		}
		return limit;
	}

	private float checkForPermit(String box_id, String measure_timestamp) {
		float limite = 0;
		
		Box box = Box.getBoxFromId(box_id);
		
		float lat = box.getCoordinatesLat();
		
		float longi = box.getCoordinatesLong();
		
		List<Permit> permits = Permit.getAllPermits();
		
		String[] timeS, timeSdate, timeShour;

		timeS = measurementTimestamp.split(" ");
		timeSdate = timeS[0].split("-"); // yyyy-mm-dd
		timeShour = timeS[1].split(":"); // hh:mm:ss

		GregorianCalendar calendarMeasure = new GregorianCalendar(Integer.parseInt(timeSdate[0]), ((Integer.parseInt(timeSdate[1])) - 1), Integer.parseInt(timeSdate[2]), Integer.parseInt(timeShour[0]), Integer.parseInt(timeShour[1]));		
		Permit permit;

		for (int i = 0; i < permits.size(); i++) {
			String[] d_begin = (permits.get(i).getBegin_date()).split("-");
			String[] d_end = (permits.get(i).getEnd_date()).split("-");
			String[] t_begin = (permits.get(i).getBegin_time()).split(":");
			String[] t_end = (permits.get(i).getEnd_time()).split(":");

			GregorianCalendar calendarBegin = new GregorianCalendar(Integer.parseInt(d_begin[2]), ((Integer.parseInt(d_begin[1])) - 1),	Integer.parseInt(d_begin[0]), Integer.parseInt(t_begin[0]), Integer.parseInt(t_begin[0]));
			GregorianCalendar calendarEnd = new GregorianCalendar(Integer.parseInt(d_end[2]), ((Integer.parseInt(d_end[1])) - 1), Integer.parseInt(d_end[0]), Integer.parseInt(t_end[0]), Integer.parseInt(t_end[0]));
			permit = permits.get(i);
			
			if (((permit.getLat4()) < lat) && (lat < (permit.getLat1())) && ((permit.getLong2()) < longi) && (longi < (permit.getLong1()))) {
				if ((calendarMeasure.after(calendarBegin)) && (calendarMeasure.after(calendarEnd))) {
					limite = permit.getMaxDB();
					break;
				}
			}
		}
		return limite;
	}

	private int checkIndicator(String timestamp) {
		int indicative;		
		String[] datetime, time;
		int hour;
		
		datetime = timestamp.split(" ");
		time = datetime[1].split(":"); // hh:mm:ss
		hour = Integer.parseInt(time[0]);
		
		if(hour >= LegalLimitsMap.DAY_HOUR && hour <= LegalLimitsMap.NIGHT_HOUR) {
			indicative = LegalLimitsMap.IND_LDEN;			
		}
		else {
			indicative = LegalLimitsMap.IND_LN;
		}
		
		return indicative;
	}

	private int checkZone(String box_id) {
		int zone_type = 0;
		int zone_id;
		Box box = Box.getBoxFromId(box_id);
		
		zone_id = Zona.verifyZone(box.getCoordinatesLat(), box.getCoordinatesLong());
		Zona zona = Zona.getZoneFromId(zone_id);
		String type = zona.getTipo().toLowerCase();		
		
		switch(type) {		
			case("mista"):
				zone_type = LegalLimitsMap.MIXED;
				break;
			case("sensivel"):
				zone_type = LegalLimitsMap.SENSITIVE;
				break;
			case("sensivel_ifta"):
				zone_type = LegalLimitsMap.SENSITIVE_IFTA;
				break;
			case("sensivel_iftna"):
				zone_type = LegalLimitsMap.SENSITIVE_IFTNA;
				break;
			case("nenhuma"):
				zone_type = LegalLimitsMap.NONE;
				break;
			default:
				zone_type  = LegalLimitsMap.NONE;
				break;
		}
		
		return zone_type;
	}

	private float calculateSDeviation(float avr) {
		float sum = 0;

		for (int i = 0; i < values.length; i++) {
			sum += (values[i] - avr) * (values[i] - avr);
		}

		return (float) Math.sqrt(sum / values.length);
	}

	private float calculateAverage() {
		float sum = 0;

		for (int i = 0; i < values.length; i++) {
			sum += values[i];
		}
		return sum / values.length;
	}

}
