package com.comerzzia.bricodepot.generarftdefs.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Vendedor {
	
	@XmlElement(name = "usuario")
	private String usuario;
	@XmlElement(name = "desusuario")
	private String desUsuario;
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getDesUsuario() {
		return desUsuario;
	}
	
	public void setDesUsuario(String desUsuario) {
		this.desUsuario = desUsuario;
	}
	
	

}
