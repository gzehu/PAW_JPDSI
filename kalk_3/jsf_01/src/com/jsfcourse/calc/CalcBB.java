package com.jsfcourse.calc;

import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


@Named
@RequestScoped
//@SessionScoped
public class CalcBB {
	private String kwota;
	private String oprocentowanie;
	private String lata;
	private Double result;
	private Double q;
	private Double years;
	
	@Inject
	FacesContext ctx;

	public String getKwota() {
		return kwota;
	}

	public void setKwota(String kwota) {
		this.kwota = kwota;
	}

	public String getoprocentowanie() {
		return oprocentowanie;
	}

	public void setoprocentowanie(String oprocentowanie) {
		this.oprocentowanie = oprocentowanie;
	}
	
	public String getlata() {
		return lata;
	}

	public void setlata(String lata) {
		this.lata = lata;
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}

	public boolean doTheMath() {
		try {
			double kwota = Double.parseDouble(this.kwota);
			double oprocentowanie = Double.parseDouble(this.oprocentowanie);
			double lata = Double.parseDouble(this.lata);
			
			q = 1+((oprocentowanie/100)/12);
			years = lata * 12;
			
			
			result = kwota * Math.pow(q, years) * (q-1)/((Math.pow(q, years))-1);
			result = (double) Math.round(result);

			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operacja wykonana poprawnie", null));
			return true;
		} catch (Exception e) {
			ctx.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd podczas przetwarzania parametrów", null));
			return false;
		}
	}

	// Go to "showresult" if ok
	public String calc() {
		if (doTheMath()) {
			return "showresult";
		}
		return null;
	}

	// Put result in messages on AJAX call
	public String calc_AJAX() {
		if (doTheMath()) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Wynik: " + result, null));
		}
		return null;
	}

	public String info() {
		return "info";
	}
}
