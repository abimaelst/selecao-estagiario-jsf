package com.planner.empresarial.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.planner.empresarial.model.Cargo;
import com.planner.empresarial.repository.Cargos;
import com.planner.empresarial.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Cargo.class)
public class CargoConverter implements Converter {

	//@Inject
	private Cargos cargos;
	
	public CargoConverter() {
	
		cargos = CDIServiceLocator.getBean(Cargos.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Cargo retorno = null;
		
		if (value != null) {
			Long id = new Long(value);
			retorno = cargos.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null && ((Cargo) value).getId() != null) {
			return ((Cargo) value).getId().toString();
		}
		
		return "";
	}

}
