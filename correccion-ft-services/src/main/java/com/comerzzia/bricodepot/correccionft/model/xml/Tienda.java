package com.comerzzia.bricodepot.correccionft.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Tienda {
	
	@XmlElement(name = "codigo")
	private String codigo;
	@XmlElement(name = "cp")
	private String cp;
	@XmlElement(name = "descripcion")
	private String descripcion;
	@XmlElement(name = "domicilio")
	private String domicilio;
	@XmlElement(name = "id_trat_impuestos")
	private String idTratImpuestos;
	@XmlElement(name = "poblacion")
	private String poblacion;
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getCp() {
		return cp;
	}
	
	public void setCp(String cp) {
		this.cp = cp;
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
	
	public String getIdTratImpuestos() {
		return idTratImpuestos;
	}
	
	public void setIdTratImpuestos(String idTratImpuestos) {
		this.idTratImpuestos = idTratImpuestos;
	}
	
	public String getPoblacion() {
		return poblacion;
	}
	
	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	
	
}
