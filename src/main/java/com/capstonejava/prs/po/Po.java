package com.capstonejava.prs.po;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.capstonejava.prs.poline.Poline;
import com.capstonejava.prs.vendor.Vendor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class Po {

	@Column(columnDefinition="decimal(11,2) NOT NULL DEFAULT 0")
	private double pototal;
	
	@JsonBackReference
	@ManyToOne(optional=false)
	private Vendor vendor;
	
	@JsonManagedReference
	@OneToMany(mappedBy="Po", orphanRemoval=true)
	private List<Poline> poline;
	
	
	
	public Po() {}



	public double getPototal() {
		return pototal;
	}



	public void setPototal(double pototal) {
		this.pototal = pototal;
	}



	public Vendor getVendor() {
		return vendor;
	}



	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}



	public List<Poline> getPoline() {
		return poline;
	}



	public void setPoline(List<Poline> poline) {
		this.poline = poline;
	} 
	
	
	
	
	
	
	
}
