package dataAccess;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Configuracao 
{

	int deltaS;
	int durationMinutes;
	String hours;
	int periodMinutes;
	int nDays;
	
	
	public Configuracao() {}
	
	public Configuracao(int deltaS, int durationMinutes, String hours, int periodMinutes, int nDays)
	{
		this.deltaS=deltaS;
		this.durationMinutes=durationMinutes;
		this.hours=hours;
		this.periodMinutes=periodMinutes;
		this.nDays=nDays;
	}
	
	
	public void setdeltaS(int deltaS) 
	{
	        this.deltaS = deltaS;
	}
	
	public int getdeltaS() 
	{
        return deltaS;
    }
	
	
	public void setdurationMinutes(int durationMinutes) 
	{
	        this.durationMinutes = durationMinutes;
	}
	
	public int getdurationMinutes() 
	{
        return durationMinutes;
    }
	
			
	public void setHours(String hours) 
	{
	        this.hours = hours;
	}
	
	public String getHours() 
	{
        return hours;
    }
	
	

	public void setperiodMinutes(int periodMinutes) 
	{
	        this.periodMinutes = periodMinutes;
	}
	
	public int getperiodMinutes() 
	{
        return periodMinutes;
    }
   
    
	public void setnDays(int nDays) 
	{
	        this.nDays = nDays;
	}
	
	public int getnDays() 
	{
        return nDays;
    }
    
	
	
}
