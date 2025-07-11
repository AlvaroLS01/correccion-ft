package com.comerzzia.bricodepot.correccionft.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CambioTotales {
	
	@XmlElement(name = "codmedpag")
	public String codMedPag;
	@XmlElement(name = "desmedpag")
	public String desMedPago;
	@XmlElement(name = "importe")
	public String importe;
	@XmlElement(name = "extendedData")
	public String extendedData;
	@XmlElement(name = "eliminable")
	public String eliminable;
	@XmlElement(name = "introducidoPorCajero")
	public String introducidoPorCajero;
	@XmlElement(name = "movimientoCajaInsertado")
	public String movimientoCajaInsertado;
	
	public String getCodMedPag() {
		return codMedPag;
	}
	
	public void setCodMedPag(String codMedPag) {
		this.codMedPag = codMedPag;
	}
	
	public String getDesMedPago() {
		return desMedPago;
	}
	
	public void setDesMedPago(String desMedPago) {
		this.desMedPago = desMedPago;
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
