package com.comerzzia.bricodepot.motivoslinea.model.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType(XmlAccessType.FIELD)
public class CabeceraTicket {
	
	@XmlAttribute(name = "xmlns:xsi")
	private String xmlns;
	@XmlAttribute(name = "xsi:type")
	private String xsi;
	
	@XmlElement(name ="uid_ticket")
	private String uidTicket;
	@XmlElement(name= "id_ticket")
	private String idTicket;
	@XmlElement(name = "cod_ticket")
	private String codTicket;
	@XmlElement(name = "uid_actividad")
	private String uidActividad;
	@XmlElement(name = "codcaja")
	private String codCaja;
	@XmlElement(name = "uid_diario_caja")
	private String uidDiarioCaja;
	@XmlElement(name= "serie_ticket")
	private String serieTicket;
	@XmlElement(name = "fecha")
	private String fecha;
	@XmlElement(name = "fecha_contable")
	private String fechaContable;
	@XmlElement(name = "tipo_documento")
	private String tipoDocumento;
	@XmlElement(name = "cod_tipo_documento")
	private String codTipDocumento;
	@XmlElement(name = "des_tipo_documento")
	private String desTipoDocumento;
	@XmlElement(name = "formato_impresion")
	private String formatoImpresion;
	@XmlElement(name = "cajero")
	private Cajero cajero;
	@XmlElement(name = "tienda")
	private Tienda tienda;
	@XmlElement(name = "cliente")
	private Cliente cliente;
	@XmlElement(name = "empresa")
	private Empresa empresa;
	@XmlElementWrapper(name = "lineasimpuestos")
	@XmlElement(name = "lineaimpuestos")
	private List<LineaImpuestos> lineasImpuestos;
	@XmlElement(name = "totales")
	private Totales totales;
	@XmlElement(name = "firma")
	private Firma firma;
	@XmlElement(name = "localizador")
	private String localizador;
	@XmlElement(name = "tickets_referenciados")
	private String ticketsReferenciados;
	@XmlElement(name = "cantidad_articulos")
	private String cantidadArticulos;
	@XmlElementWrapper(name = "eventos_auditoria")
	@XmlElement(name = "evento")
	private List<EventoAuditoria> eventosAuditoria;
	@XmlElement(name = "codigo_postal_venta")
	private String codigoPostalVenta;
	
	public String getXmlns() {
		return xmlns;
	}
	
	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}
	
	public String getXsi() {
		return xsi;
	}
	
	public void setXsi(String xsi) {
		this.xsi = xsi;
	}
	
	public String getUidTicket() {
		return uidTicket;
	}
	
	public void setUidTicket(String uidTicket) {
		this.uidTicket = uidTicket;
	}
	
	public String getIdTicket() {
		return idTicket;
	}
	
	public void setIdTicket(String idTicket) {
		this.idTicket = idTicket;
	}
	
	public String getCodTicket() {
		return codTicket;
	}
	
	public void setCodTicket(String codTicket) {
		this.codTicket = codTicket;
	}
	
	public String getUidActividad() {
		return uidActividad;
	}
	
	public void setUidActividad(String uidActividad) {
		this.uidActividad = uidActividad;
	}
	
	public String getCodCaja() {
		return codCaja;
	}
	
	public void setCodCaja(String codCaja) {
		this.codCaja = codCaja;
	}
	
	public String getUidDiarioCaja() {
		return uidDiarioCaja;
	}
	
	public void setUidDiarioCaja(String uidDiarioCaja) {
		this.uidDiarioCaja = uidDiarioCaja;
	}
	
	public String getSerieTicket() {
		return serieTicket;
	}
	
	public void setSerieTicket(String serieTicket) {
		this.serieTicket = serieTicket;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public String getFechaContable() {
		return fechaContable;
	}
	
	public void setFechaContable(String fechaContable) {
		this.fechaContable = fechaContable;
	}
	
	public String getCodTipDocumento() {
		return codTipDocumento;
	}
	
	public void setCodTipDocumento(String codTipDocumento) {
		this.codTipDocumento = codTipDocumento;
	}
	
	public String getDesTipoDocumento() {
		return desTipoDocumento;
	}
	
	public void setDesTipoDocumento(String desTipoDocumento) {
		this.desTipoDocumento = desTipoDocumento;
	}
	
	public String getFormatoImpresion() {
		return formatoImpresion;
	}
	
	public void setFormatoImpresion(String formatoImpresion) {
		this.formatoImpresion = formatoImpresion;
	}
	
	public Cajero getCajero() {
		return cajero;
	}
	
	public void setCajero(Cajero cajero) {
		this.cajero = cajero;
	}
	
	public Tienda getTienda() {
		return tienda;
	}
	
	public void setTienda(Tienda tienda) {
		this.tienda = tienda;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
	
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public List<LineaImpuestos> getLineasImpuestos() {
		return lineasImpuestos;
	}
	
	public void setLineasImpuestos(List<LineaImpuestos> lineasImpuestos) {
		this.lineasImpuestos = lineasImpuestos;
	}
	
	public Totales getTotales() {
		return totales;
	}
	
	public void setTotales(Totales totales) {
		this.totales = totales;
	}
	
	public Firma getFirma() {
		return firma;
	}
	
	public void setFirma(Firma firma) {
		this.firma = firma;
	}
	
	public String getLocalizador() {
		return localizador;
	}
	
	public void setLocalizador(String localizador) {
		this.localizador = localizador;
	}
	
	public String getTicketsReferenciados() {
		return ticketsReferenciados;
	}
	
	public void setTicketsReferenciados(String ticketsReferenciados) {
		this.ticketsReferenciados = ticketsReferenciados;
	}
	
	public String getCantidadArticulos() {
		return cantidadArticulos;
	}
	
	public void setCantidadArticulos(String cantidadArticulos) {
		this.cantidadArticulos = cantidadArticulos;
	}
	
	public List<EventoAuditoria> getEventosAuditoria() {
		return eventosAuditoria;
	}
	
	public void setEventosAuditoria(List<EventoAuditoria> eventosAuditoria) {
		this.eventosAuditoria = eventosAuditoria;
	}
	
	public String getCodigoPostalVenta() {
		return codigoPostalVenta;
	}
	
	public void setCodigoPostalVenta(String codigoPostalVenta) {
		this.codigoPostalVenta = codigoPostalVenta;
	}

	
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	
	
	

}
