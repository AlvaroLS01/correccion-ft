package com.comerzzia.bricodepot.motivoslinea.model.bbdd;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Motivo {
	
	private String uidActividad;
	private String uidTicket;
	private String codAlbaran;
	private String codAlbaranOrigen;
	private LocalDate fecha;
	private Integer linea;
	private String codArticulo;
	private String tipoMotivo;
	private String codigoMotivo;
	private String descripcionMotivo;
	private String comentarioMotivo;
	private BigDecimal cantidad;
	private BigDecimal precioOriginal;
	private BigDecimal precioFinal;
	
	public String getUidActividad() {
		return uidActividad;
	}
	
	public void setUidActividad(String uidActividad) {
		this.uidActividad = uidActividad;
	}
	
	public String getUidTicket() {
		return uidTicket;
	}
	
	public void setUidTicket(String uidTickeT) {
		this.uidTicket = uidTickeT;
	}
	
	public String getCodAlbaran() {
		return codAlbaran;
	}
	
	public void setCodAlbaran(String codAlbaran) {
		this.codAlbaran = codAlbaran;
	}
	
	public String getCodAlbaranOrigen() {
		return codAlbaranOrigen;
	}
	
	public void setCodAlbaranOrigen(String codAlbaranOrigen) {
		this.codAlbaranOrigen = codAlbaranOrigen;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}
	
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	public Integer getLinea() {
		return linea;
	}
	
	public void setLinea(Integer linea) {
		this.linea = linea;
	}
	
	public String getCodArticulo() {
		return codArticulo;
	}
	
	public void setCodArticulo(String codArticulo) {
		this.codArticulo = codArticulo;
	}
	
	public String getTipoMotivo() {
		return tipoMotivo;
	}
	
	public void setTipoMotivo(String tipoMotivo) {
		this.tipoMotivo = tipoMotivo;
	}
	
	public String getCodigoMotivo() {
		return codigoMotivo;
	}
	
	public void setCodigoMotivo(String codigoMotivo) {
		this.codigoMotivo = codigoMotivo;
	}
	
	public String getDescripcionMotivo() {
		return descripcionMotivo;
	}
	
	public void setDescripcionMotivo(String descripcionMotivo) {
		this.descripcionMotivo = descripcionMotivo;
	}
	
	public String getComentarioMotivo() {
		return comentarioMotivo;
	}
	
	public void setComentarioMotivo(String comentarioMotivo) {
		this.comentarioMotivo = comentarioMotivo;
	}
	
	public BigDecimal getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}
	
	public BigDecimal getPrecioOriginal() {
		return precioOriginal;
	}
	
	public void setPrecioOriginal(BigDecimal precioOriginal) {
		this.precioOriginal = precioOriginal;
	}
	
	public BigDecimal getPrecioFinal() {
		return precioFinal;
	}
	
	public void setPrecioFinal(BigDecimal precioFinal) {
		this.precioFinal = precioFinal;
	}
	
	

}
