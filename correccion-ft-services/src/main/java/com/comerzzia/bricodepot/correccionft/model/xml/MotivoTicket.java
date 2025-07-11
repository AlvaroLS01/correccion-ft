package com.comerzzia.bricodepot.correccionft.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class MotivoTicket {

	@XmlElement(name = "uidActividad")
	private String uidActividad;
	@XmlElement(name = "codigo")
	private String codigo;
	@XmlElement(name = "codigo_tipo")
	private String codigoTipo;
	@XmlElement(name = "descripcion")
	private String descripcion;
	@XmlElement(name = "comentario")
	private String comentario;
	@XmlElement(name = "precio_articulo_original")
	private String precioArticuloOriginal;
	@XmlElement(name = "precio_articulo_aplicado")
	private String precioArticuloAplicado;
	
	public String getUidActividad() {
		return uidActividad;
	}
	
	public void setUidActividad(String uidActividad) {
		this.uidActividad = uidActividad;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getCodigoTipo() {
		return codigoTipo;
	}
	
	public void setCodigoTipo(String codigoTipo) {
		this.codigoTipo = codigoTipo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getComentario() {
		return comentario;
	}
	
	public void setComentario(String comentario) {
		this.comentario = comentario;
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
	
	
}
