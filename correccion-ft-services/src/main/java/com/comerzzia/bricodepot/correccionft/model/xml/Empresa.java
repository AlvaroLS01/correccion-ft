package com.comerzzia.bricodepot.correccionft.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Empresa {
	
	@XmlElement(name = "codigo")
	private String codigo;
	@XmlElement(name = "descripcion")
	private String descripcion;
	@XmlElement(name = "domicilio")
	private String domicilio;
	@XmlElement(name = "provincia")
	private String provincia;
	@XmlElement(name = "cp")
	private String cp;
	@XmlElement(name = "cif")
	private String cif;
	@XmlElement(name = "nombre_comercial")
	private String nombreComercial;
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getDomicilio() {
		return domicilio;
	}
	
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	
	public String getProvincia() {
		return provincia;
	}
	
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	public String getCp() {
		return cp;
	}
	
	public void setCp(String cp) {
		this.cp = cp;
	}
	
	public String getCif() {
		return cif;
	}
	
	public void setCif(String cif) {
		this.cif = cif;
	}
	
	public String getNombreComercial() {
		return nombreComercial;
	}
	
	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}
	
	

}
