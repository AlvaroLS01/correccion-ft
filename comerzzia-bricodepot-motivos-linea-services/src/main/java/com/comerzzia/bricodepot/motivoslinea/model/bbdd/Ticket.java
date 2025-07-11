package com.comerzzia.bricodepot.motivoslinea.model.bbdd;


public class Ticket {
	
	private String xml;
	
	private Integer idTipoDocumento; 
	
	private String uidTicket;

	
	public String getXml() {
		return xml;
	}

	
	public void setXml(String xml) {
		this.xml = xml;
	}


	
	public Integer getIdTipoDocumento() {
		return idTipoDocumento;
	}


	
	public void setIdTipoDocumento(Integer idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}


	
	public String getUidTicket() {
		return uidTicket;
	}


	
	public void setUidTicket(String uidTicket) {
		this.uidTicket = uidTicket;
	}

}
