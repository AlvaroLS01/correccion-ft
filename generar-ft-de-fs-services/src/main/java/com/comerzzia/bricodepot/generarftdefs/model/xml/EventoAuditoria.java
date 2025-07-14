package com.comerzzia.bricodepot.generarftdefs.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class EventoAuditoria {
	
	@XmlElement(name = "uid_actividad")
	private String uidActividad;
	@XmlElement(name = "cod_almacen")
	private String codAlmacen;
	@XmlElement(name = "fecha")
	private String fecha;
	@XmlElement(name = "tipo")
	private String tipo;
	@XmlElement(name = "des_evento")
	private String desEvento;
	@XmlElement(name = "procede")
	private String procede;
	@XmlElement(name = "id_usuario")
	private String idUsuario;
	@XmlElement(name = "des_usuario")
	private String desUsuario;
	@XmlElement(name = "id_supervisor")
	private String idSupervisor;
	@XmlElement(name = "des_supervisor")
	private String desSupervisor;
	@XmlElement(name = "cod_articulo")
	private String codArticulo;
	@XmlElement(name = "des_articulo")
	private String desArticulo;
	@XmlElement(name = "can_articulo")
	private String canArticulo;
	@XmlElement(name = "precio_articulo_original")
	private String precioArticuloOriginal;
	@XmlElement(name = "precio_articulo_aplicado")
	private String precioArticuloAplicado;
	@XmlElement(name = "linea_referencia")
	private String lineaReferencia;
	
	public String getUidActividad() {
		return uidActividad;
	}
	
	public void setUidActividad(String uidActividad) {
		this.uidActividad = uidActividad;
	}
	
	public String getCodAlmacen() {
		return codAlmacen;
	}
	
	public void setCodAlmacen(String codAlmacen) {
		this.codAlmacen = codAlmacen;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getDesEvento() {
		return desEvento;
	}
	
	public void setDesEvento(String desEvento) {
		this.desEvento = desEvento;
	}
	
	public String getProcede() {
		return procede;
	}
	
	public void setProcede(String procede) {
		this.procede = procede;
	}
	
	public String getIdUsuario() {
		return idUsuario;
	}
	
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public String getDesUsuario() {
		return desUsuario;
	}
	
	public void setDesUsuario(String desUsuario) {
		this.desUsuario = desUsuario;
	}
	
	public String getIdSupervisor() {
		return idSupervisor;
	}
	
	public void setIdSupervisor(String idSupervisor) {
		this.idSupervisor = idSupervisor;
	}
	
	public String getDesSupervisor() {
		return desSupervisor;
	}
	
	public void setDesSupervisor(String desSupervisor) {
		this.desSupervisor = desSupervisor;
	}
	
	public String getCodArticulo() {
		return codArticulo;
	}
	
	public void setCodArticulo(String codArticulo) {
		this.codArticulo = codArticulo;
	}
	
	public String getDesArticulo() {
		return desArticulo;
	}
	
	public void setDesArticulo(String desArticulo) {
		this.desArticulo = desArticulo;
	}
	
	public String getCanArticulo() {
		return canArticulo;
	}
	
	public void setCanArticulo(String canArticulo) {
		this.canArticulo = canArticulo;
	}
	
	public String getPrecioArticuloOriginal() {
		return precioArticuloOriginal;
	}
	
	public void setPrecioArticuloOriginal(String precioArticuloOriginal) {
		this.precioArticuloOriginal = precioArticuloOriginal;
	}
	
	public String getPrecioArticuloAplicado() {
		return precioArticuloAplicado;
	}
	
	public void setPrecioArticuloAplicado(String precioArticuloAplicado) {
		this.precioArticuloAplicado = precioArticuloAplicado;
	}
	
	public String getLineaReferencia() {
		return lineaReferencia;
	}
	
	public void setLineaReferencia(String lineaReferencia) {
		this.lineaReferencia = lineaReferencia;
	}
	
	
}
