package com.comerzzia.bricodepot.generarftdefs.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class LineaImpuestos {

	@XmlElement(name = "base")
	private String base;
	@XmlElement(name = "codImp")
	private String codImp;
	@XmlElement(name = "cuota")
	private String cuota;
	@XmlElement(name = "cuotaRecargo")
	private String cuotaRecargo;
	@XmlElement(name = "impuestos")
	private String impuestos;
	@XmlElement(name = "porcentaje")
	private String porcentaje;
	@XmlElement(name = "porcentajeRecargo")
	private String porcentajeRecargo;
	@XmlElement(name = "total")
	private String total;
	
	public String getBase() {
		return base;
	}
	
	public void setBase(String base) {
		this.base = base;
	}
	
	public String getCodImp() {
		return codImp;
	}
	
	public void setCodImp(String codImp) {
		this.codImp = codImp;
	}
	
	public String getCuota() {
		return cuota;
	}
	
	public void setCuota(String cuota) {
		this.cuota = cuota;
	}
	
	public String getCuotaRecargo() {
		return cuotaRecargo;
	}
	
	public void setCuotaRecargo(String cuotaRecargo) {
		this.cuotaRecargo = cuotaRecargo;
	}
	
	public String getImpuestos() {
		return impuestos;
	}
	
	public void setImpuestos(String impuestos) {
		this.impuestos = impuestos;
	}
	
	public String getPorcentaje() {
		return porcentaje;
	}
	
	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}
	
	public String getPorcentajeRecargo() {
		return porcentajeRecargo;
	}
	
	public void setPorcentajeRecargo(String porcentajeRecargo) {
		this.porcentajeRecargo = porcentajeRecargo;
	}
	
	public String getTotal() {
		return total;
	}
	
	public void setTotal(String total) {
		this.total = total;
	}
	
	
}
