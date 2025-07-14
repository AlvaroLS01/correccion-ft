package com.comerzzia.bricodepot.generarftdefs.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Totales {
	
	@XmlElement(name = "base")
	private String base;
	@XmlElement(name = "impuestos")	
	private String impuestos;
	@XmlElement(name = "total")
	private String total;
	@XmlElement(name = "total_a_pagar")
	private String totalAPagar;
	@XmlElement(name = "total_sin_promociones")
	private String totalSinPromociones;
	@XmlElement(name = "total_promociones")
	private String totalPromociones;
	@XmlElement(name = "total_promociones_cabecera")
	private String totalPromocionesCabecera;
	@XmlElement(name = "total_promociones_lineas")
	private String totalPromocionesLineas;
	@XmlElement(name = "entregado")
	private String entregado;
	@XmlElement(name = "entregadoACuenta")
	private String entregadoACuenta;
	@XmlElement(name = "cambio")
	private CambioTotales cambio;
	@XmlElement(name = "puntos")
	private String puntos;
	
	public String getBase() {
		return base;
	}
	
	public void setBase(String base) {
		this.base = base;
	}
	
	public String getImpuestos() {
		return impuestos;
	}
	
	public void setImpuestos(String impuestos) {
		this.impuestos = impuestos;
	}
	
	public String getTotal() {
		return total;
	}
	
	public void setTotal(String total) {
		this.total = total;
	}
	
	public String getTotalAPagar() {
		return totalAPagar;
	}
	
	public void setTotalAPagar(String totalAPagar) {
		this.totalAPagar = totalAPagar;
	}
	
	public String getTotalSinPromociones() {
		return totalSinPromociones;
	}
	
	public void setTotalSinPromociones(String totalSinPromociones) {
		this.totalSinPromociones = totalSinPromociones;
	}
	
	public String getTotalPromociones() {
		return totalPromociones;
	}
	
	public void setTotalPromociones(String totalPromociones) {
		this.totalPromociones = totalPromociones;
	}
	
	public String getTotalPromocionesCabecera() {
		return totalPromocionesCabecera;
	}
	
	public void setTotalPromocionesCabecera(String totalPromocionesCabecera) {
		this.totalPromocionesCabecera = totalPromocionesCabecera;
	}
	
	public String getTotalPromocionesLineas() {
		return totalPromocionesLineas;
	}
	
	public void setTotalPromocionesLineas(String totalPromocionesLineas) {
		this.totalPromocionesLineas = totalPromocionesLineas;
	}
	
	public String getEntregado() {
		return entregado;
	}
	
	public void setEntregado(String entregado) {
		this.entregado = entregado;
	}
	
	public String getEntregadoACuenta() {
		return entregadoACuenta;
	}
	
	public void setEntregadoACuenta(String entregadoACuenta) {
		this.entregadoACuenta = entregadoACuenta;
	}
	
	public CambioTotales getCambio() {
		return cambio;
	}
	
	public void setCambio(CambioTotales cambio) {
		this.cambio = cambio;
	}
	
	public String getPuntos() {
		return puntos;
	}
	
	public void setPuntos(String puntos) {
		this.puntos = puntos;
	}
	
	
	

}
