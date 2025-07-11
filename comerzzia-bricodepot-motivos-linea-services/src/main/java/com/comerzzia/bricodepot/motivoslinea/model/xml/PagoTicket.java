package com.comerzzia.bricodepot.motivoslinea.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PagoTicket {
	
	@XmlElement(name = "codmedpag")
	private String codMedPag;
	@XmlElement(name = "payment_id")
	private String paymentId;
	@XmlElement(name = "desmedpag")
	private String desMedPag;
	@XmlElement(name = "importe")
	private String importe;
	@XmlElement(name = "extendedData")
	private String extendedData;
	@XmlElement(name = "eliminable")
	private String eliminable;
	@XmlElement(name = "introducidoPorCajero")
	private String introducidoPorCajero;
	@XmlElement(name = "movimientoCajaInsertado")
	private String movimientoCajaInsertado;
	
	public String getCodMedPag() {
		return codMedPag;
	}
	
	public void setCodMedPag(String codMedPag) {
		this.codMedPag = codMedPag;
	}
	
	public String getPaymentId() {
		return paymentId;
	}
	
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
	public String getDesMedPag() {
		return desMedPag;
	}
	
	public void setDesMedPag(String desMedPag) {
		this.desMedPag = desMedPag;
	}
	
	public String getImporte() {
		return importe;
	}
	
	public void setImporte(String importe) {
		this.importe = importe;
	}
	
	public String getExtendedData() {
		return extendedData;
	}
	
	public void setExtendedData(String extendedData) {
		this.extendedData = extendedData;
	}
	
	public String getEliminable() {
		return eliminable;
	}
	
	public void setEliminable(String eliminable) {
		this.eliminable = eliminable;
	}
	
	public String getIntroducidoPorCajero() {
		return introducidoPorCajero;
	}
	
	public void setIntroducidoPorCajero(String introducidoPorCajero) {
		this.introducidoPorCajero = introducidoPorCajero;
	}
	
	public String getMovimientoCajaInsertado() {
		return movimientoCajaInsertado;
	}
	
	public void setMovimientoCajaInsertado(String movimientoCajaInsertado) {
		this.movimientoCajaInsertado = movimientoCajaInsertado;
	}

	
}
