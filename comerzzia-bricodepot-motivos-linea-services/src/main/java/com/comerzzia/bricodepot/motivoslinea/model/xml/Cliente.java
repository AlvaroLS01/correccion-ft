package com.comerzzia.bricodepot.motivoslinea.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Cliente {
	
	@XmlElement(name = "codcli")
	private String codCli;
	@XmlElement(name = "descli")
	private String desCli;
	@XmlElement(name = "domicilio")
	private String domicilio;
	@XmlElement(name = "poblacion")
	private String poblacion;
	@XmlElement(name = "cp")
	private String cp;
	@XmlElement(name = "pais")
	private String pais;
	@XmlElement(name = "cif")
	private String cif;
	@XmlElement(name = "id_trat_impuestos")
	private String idTratImpuestos;
	@XmlElement(name = "localidad")
	private String localidad;
	@XmlElement(name = "id_grupo_impuestos")
	private String idGrupoImpuestos;
	
	public String getCodCli() {
		return codCli;
	}
	
	public void setCodCli(String codCli) {
		this.codCli = codCli;
	}
	
	public String getDesCli() {
		return desCli;
	}
	
	public void setDesCli(String desCli) {
		this.desCli = desCli;
	}
	
	public String getDomicilio() {
		return domicilio;
	}
	
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	
	public String getPoblacion() {
		return poblacion;
	}
	
	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}
	
	public String getCp() {
		return cp;
	}
	
	public void setCp(String cp) {
		this.cp = cp;
	}
	
	public String getPais() {
		return pais;
	}
	
	public void setPais(String pais) {
		this.pais = pais;
	}
	
	public String getCif() {
		return cif;
	}
	
	public void setCif(String cif) {
		this.cif = cif;
	}
	
	public String getIdTratImpuestos() {
		return idTratImpuestos;
	}
	
	public void setIdTratImpuestos(String idTratImpuestos) {
		this.idTratImpuestos = idTratImpuestos;
	}
	
	public String getLocalidad() {
		return localidad;
	}
	
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	
	public String getIdGrupoImpuestos() {
		return idGrupoImpuestos;
	}
	
	public void setIdGrupoImpuestos(String idGrupoImpuestos) {
		this.idGrupoImpuestos = idGrupoImpuestos;
	}
	
	

}
