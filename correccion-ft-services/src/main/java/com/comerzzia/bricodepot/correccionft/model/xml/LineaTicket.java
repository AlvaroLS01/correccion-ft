package com.comerzzia.bricodepot.correccionft.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class LineaTicket {

	@XmlAttribute(name = "xmlns:xsi")
	private String xmlns;
	@XmlAttribute(name = "xsi:type")
	private String xsi;
	@XmlAttribute(name = "idlinea")
	private String idLinea;
	@XmlElement(name = "codart")
	private String codArt;
	@XmlElement(name = "desart")
	private String desArt;
	@XmlElement(name = "cantidad")
	private String cantidad;
	@XmlElement(name = "desglose1")
	private String desglose1;
	@XmlElement(name = "desglose2")
	private String desglose2;
	@XmlElement(name = "generico")
	private String generico;
	@XmlElement(name = "vendedor")
	private Vendedor vendedor;
	@XmlElement(name = "editable")
	private String editable;
	@XmlElement(name = "codImp")
	private String codImp;
	@XmlElement(name = "codtar")
	private String codTar;
	@XmlElement(name = "precio_tarifa_origen")
	private String precioTarifaOrigen;
	@XmlElement(name = "precio_total_tarifa_origen")
	private String precioTotalTarifaOrigen;
	@XmlElement(name = "precio_sin_dto")
	private String precioSinDto;
	@XmlElement(name = "precio_total_sin_dto")
	private String precioTotalSinDto;
	@XmlElement(name = "precio")
	private String precio;
	@XmlElement(name = "precio_total")
	private String precioTotal;
	@XmlElement(name = "importe")
	private String importe;
	@XmlElement(name = "importe_total")
	private String importeTotal;
	@XmlElement(name = "descuento")
	private String descuento;
	@XmlElement(name = "descuento_manual")
	private String descuentoManual;
	@XmlElement(name = "importeTotalPromociones")
	private String importeTotalPromociones;
	@XmlElement(name = "importeTotalPromocionesMenosIngreso")
	private String importeTotalPromocionesMenosIngreso;
	@XmlElement(name = "promociones")
	private String promociones;
	@XmlElement(name = "balanzaTipoArticulo")
	private String balanzaTipoArticulo;
	@XmlElement(name = "update_stock")
	private String updateStock;
	@XmlElement(name = "motivo")
	private MotivoTicket motivo;
	@XmlElement(name = "isAnticipo")
	private String isAnticipo;
	
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
	
	public String getIdLinea() {
		return idLinea;
	}
	
	public void setIdLinea(String idLinea) {
		this.idLinea = idLinea;
	}
	
	public String getCodArt() {
		return codArt;
	}
	
	public void setCodArt(String codArt) {
		this.codArt = codArt;
	}
	
	public String getDesArt() {
		return desArt;
	}
	
	public void setDesArt(String desArt) {
		this.desArt = desArt;
	}
	
	public String getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	
	public String getDesglose1() {
		return desglose1;
	}
	
	public void setDesglose1(String desglose1) {
		this.desglose1 = desglose1;
	}
	
	public String getDesglose2() {
		return desglose2;
	}
	
	public void setDesglose2(String desglose2) {
		this.desglose2 = desglose2;
	}
	
	public String getGenerico() {
		return generico;
	}
	
	public void setGenerico(String generico) {
		this.generico = generico;
	}
	
	public Vendedor getVendedor() {
		return vendedor;
	}
	
	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}
	
	public String getEditable() {
		return editable;
	}
	
	public void setEditable(String editable) {
		this.editable = editable;
	}
	
	public String getCodImp() {
		return codImp;
	}
	
	public void setCodImp(String codImp) {
		this.codImp = codImp;
	}
	
	public String getCodTar() {
		return codTar;
	}
	
	public void setCodTar(String codTar) {
		this.codTar = codTar;
	}
	
	public String getPrecioTarifaOrigen() {
		return precioTarifaOrigen;
	}
	
	public void setPrecioTarifaOrigen(String precioTarifaOrigen) {
		this.precioTarifaOrigen = precioTarifaOrigen;
	}
	
	public String getPrecioTotalTarifaOrigen() {
		return precioTotalTarifaOrigen;
	}
	
	public void setPrecioTotalTarifaOrigen(String precioTotalTarifaOrigen) {
		this.precioTotalTarifaOrigen = precioTotalTarifaOrigen;
	}
	
	public String getPrecioSinDto() {
		return precioSinDto;
	}
	
	public void setPrecioSinDto(String precioSinDto) {
		this.precioSinDto = precioSinDto;
	}
	
	public String getPrecioTotalSinDto() {
		return precioTotalSinDto;
	}
	
	public void setPrecioTotalSinDto(String precioTotalSinDto) {
		this.precioTotalSinDto = precioTotalSinDto;
	}
	
	public String getPrecio() {
		return precio;
	}
	
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	
	public String getPrecioTotal() {
		return precioTotal;
	}
	
	public void setPrecioTotal(String precioTotal) {
		this.precioTotal = precioTotal;
	}
	
	public String getImporte() {
		return importe;
	}
	
	public void setImporte(String importe) {
		this.importe = importe;
	}
	
	public String getImporteTotal() {
		return importeTotal;
	}
	
	public void setImporteTotal(String importeTotal) {
		this.importeTotal = importeTotal;
	}
	
	public String getDescuento() {
		return descuento;
	}
	
	public void setDescuento(String descuento) {
		this.descuento = descuento;
	}
	
	public String getDescuentoManual() {
		return descuentoManual;
	}
	
	public void setDescuentoManual(String descuentoManual) {
		this.descuentoManual = descuentoManual;
	}
	
	public String getImporteTotalPromociones() {
		return importeTotalPromociones;
	}
	
	public void setImporteTotalPromociones(String importeTotalPromociones) {
		this.importeTotalPromociones = importeTotalPromociones;
	}
	
	public String getImporteTotalPromocionesMenosIngreso() {
		return importeTotalPromocionesMenosIngreso;
	}
	
	public void setImporteTotalPromocionesMenosIngreso(String importeTotalPromocionesMenosIngreso) {
		this.importeTotalPromocionesMenosIngreso = importeTotalPromocionesMenosIngreso;
	}
	
	public String getPromociones() {
		return promociones;
	}
	
	public void setPromociones(String promociones) {
		this.promociones = promociones;
	}
	
	public String getBalanzaTipoArticulo() {
		return balanzaTipoArticulo;
	}
	
	public void setBalanzaTipoArticulo(String balanzaTipoArticulo) {
		this.balanzaTipoArticulo = balanzaTipoArticulo;
	}
	
	public String getUpdateStock() {
		return updateStock;
	}
	
	public void setUpdateStock(String updateStock) {
		this.updateStock = updateStock;
	}
	
	public MotivoTicket getMotivo() {
		return motivo;
	}
	
	public void setMotivo(MotivoTicket motivo) {
		this.motivo = motivo;
	}
	
	public String getIsAnticipo() {
		return isAnticipo;
	}
	
	public void setIsAnticipo(String isAnticipo) {
		this.isAnticipo = isAnticipo;
	}
	
	
	
}
