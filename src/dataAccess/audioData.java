package dataAccess;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class audioData {

	String boxId;
	String measureTimestamp;
	byte [] audioByteStream;
	
	public audioData () {}
	
	public audioData (String boxId, String measureTimestamp, byte [] audioByteStream)
	{
		this.boxId=boxId;
		this.measureTimestamp=measureTimestamp;
		this.audioByteStream=audioByteStream;
	}

	public void insertAudioData() {
		// TODO Auto-generated method stub
		
	}
	
	
}
