package com.comerzzia.bricodepot.generarftdefs.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class Firma {
	
	@XmlAttribute(name = "HASHCONTROL")
	private String hashcontrol;
	@XmlValue
	private String value;
	
	public String getHashcontrol() {
		return hashcontrol;
	}
	
	public void setHashcontrol(String hashcontrol) {
		this.hashcontrol = hashcontrol;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	

}
