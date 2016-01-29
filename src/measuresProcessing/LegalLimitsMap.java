package measuresProcessing;

public class LegalLimitsMap {
	
	public static int DAY_HOUR = 7, NIGHT_HOUR = 23;
	public static int IND_LDEN = 0, IND_LN = 1; 
	public static int MIXED = 3, SENSITIVE = 5, SENSITIVE_IFTA = 7, SENSITIVE_IFTNA = 9, NONE = 13;	
	
	public LegalLimitsMap() {				
	}
	
	public float getLimit(int area_type, int noise_indicator) {
		float limit = 0;
		
		switch(area_type - noise_indicator) {
		case(2):
			limit = 65;	//mixed area + Ln
			break;
		case(3):
			limit = 55; //mixed area + Lden
			break;
		case(4):
			limit = 45; //sensitive area + Ln
			break;
		case(5):
			limit = 55; //sensitive area + Lden
			break;
		case(6):
			limit = 65; //sensitive area near aerial transport infrastructures + Ln
			break;
		case(7):
			limit = 55;	//sensitive area near aerial transport infrastructures + Lden
			break;
		case(8):		
			limit = 60; //sensitive aerial near ground transport infrastructures + Ln
			break;
		case(9):
			limit = 50; //sensitive aerial near ground transport infrastructures + Ln
			break;
		case(12):
			limit = 63;	//unknown area + Ln
			break;
		case(13):
			limit = 53; //unknown area + Lden
			break;
		default:
			limit = 63;
			break;					
		}
		return limit;
	}
	
}
