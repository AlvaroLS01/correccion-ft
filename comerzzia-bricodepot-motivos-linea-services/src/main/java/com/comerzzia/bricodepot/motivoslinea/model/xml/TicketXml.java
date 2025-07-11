package com.comerzzia.bricodepot.motivoslinea.model.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketXml {
	
	@XmlAttribute
	private String schemaVersion;
	@XmlAttribute
	private String softwareVersion;
	@XmlAttribute
	private String localCopyVersion;
	
	@XmlElement(name = "cabecera")
	private CabeceraTicket cabeceraTicket;
	
	@XmlElementWrapper(name = "lineas")
	@XmlElement(name = "linea")
	private List<LineaTicket> lineas;
	
	@XmlElementWrapper(name ="pagos")
	@XmlElement(name = "pago")
	private List<PagoTicket> pagos;
	
	@XmlElementWrapper(name ="cuponesAplicados")
	@XmlElement(name ="cuponAplicado")
	private List<CuponAplicadoTicket> cuponesAplicados; 
	
	@XmlElementWrapper(name = "promociones")
	@XmlElement(name = "promocion")
	private List<PromocionTicket> promociones;

	@XmlElementWrapper(name ="cupones")
	@XmlElement(name ="cupon")
	private List<CuponTicket> cupones;

	
	public String getSchemaVersion() {
		return schemaVersion;
	}

	
	public void setSchemaVersion(String schemaVersion) {
		this.schemaVersion = schemaVersion;
	}

	
	public String getSoftwareVersion() {
		return softwareVersion;
	}

	
	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	
	public String getLocalCopyVersion() {
		return localCopyVersion;
	}

	
	public void setLocalCopyVersion(String localCopyVersion) {
		this.localCopyVersion = localCopyVersion;
	}

	
	public CabeceraTicket getCabeceraTicket() {
		return cabeceraTicket;
	}

	
	public void setCabeceraTicket(CabeceraTicket cabeceraTicket) {
		this.cabeceraTicket = cabeceraTicket;
	}

	
	public List<LineaTicket> getLineas() {
		return lineas;
	}

	
	public void setLineas(List<LineaTicket> lineas) {
		this.lineas = lineas;
	}

	
	public List<PagoTicket> getPagos() {
		return pagos;
	}

	
	public void setPagos(List<PagoTicket> pagos) {
		this.pagos = pagos;
	}

	
	public List<CuponAplicadoTicket> getCuponesAplicados() {
		return cuponesAplicados;
	}

	
	public void setCuponesAplicados(List<CuponAplicadoTicket> cuponesAplicados) {
		this.cuponesAplicados = cuponesAplicados;
	}

	
	public List<PromocionTicket> getPromociones() {
		return promociones;
	}

	
	public void setPromociones(List<PromocionTicket> promociones) {
		this.promociones = promociones;
	}

	
	public List<CuponTicket> getCupones() {
		return cupones;
	}

	
	public void setCupones(List<CuponTicket> cupones) {
		this.cupones = cupones;
	} 
	
	
	
	
}
