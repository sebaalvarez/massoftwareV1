package com.massoftware.windows;

import java.util.ArrayList;
import java.util.List;

import com.massoftware.model.Entity;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.themes.ValoTheme;

public class ComboBoxEntity extends ComboBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1040125093311821579L;

	@SuppressWarnings("rawtypes")
	public ComboBoxEntity(BeanItem dtoBI, String attName, String mode, List options) throws Exception {
		init(dtoBI, attName, mode, options, null);
	}
	
	@SuppressWarnings("rawtypes")
	public ComboBoxEntity(BeanItem dtoBI, String attName, String mode, List options, Object selectItem) throws Exception {
		init(dtoBI, attName, mode, options, selectItem);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void init(BeanItem dtoBI, String attName, String mode, List options, Object selectItem) throws Exception {

		String label = ((Entity) dtoBI.getBean()).label(attName);
		String labelError = ((Entity) dtoBI.getBean()).labelError(attName);
		if(labelError == null || labelError.trim().length() == 0) {
			labelError  = label;
		}
		boolean required = ((Entity) dtoBI.getBean()).required(attName);
		boolean readOnly = ((Entity) dtoBI.getBean()).readOnly(attName);
		boolean unique = ((Entity) dtoBI.getBean()).unique(attName);
		float columns = ((Entity) dtoBI.getBean()).columns(attName);

		// ----------------------------------------------------------------------------

		addStyleName(ValoTheme.COMBOBOX_TINY);

		setWidth("100%");
		setHeightUndefined();		
		setValidationVisible(true);
		setVisible(true);
		setEnabled(true);
		setReadOnly(false);
		setImmediate(true);
		setFilteringMode(FilteringMode.CONTAINS);

		// ----------------------------------------------------------------------------

		if (columns > 0) {
			setWidth(columns, Unit.EM);
		}

		if (unique) {
			addValidator(new UniqueValidator(dtoBI.getItemProperty(attName).getType(), mode, attName, dtoBI));
		}

		// ----------------------------------------------------------------------------

		setCaption(label);

		setRequiredError("El campo '" + labelError + "' es requerido. Es decir no debe estar vacio.");
		setRequired(required);
		if (isRequired()) {
			setNullSelectionAllowed(false);
		}

		// ----------------------------------------------------------------------------

		BeanItemContainer optionsBIC = new BeanItemContainer(dtoBI.getItemProperty(attName).getType(), new ArrayList());

		for (Object option : options) {
			optionsBIC.addBean(option);
		}
		setContainerDataSource(optionsBIC);

		// ----------------------------------------------------------------------------

		if(selectItem != null && optionsBIC.size() > 0) {
			
			setValue(selectItem);
			
		} else if (isRequired() && optionsBIC.size() > 0) {
			
			setValue(optionsBIC.getIdByIndex(0));
		}		

		// ----------------------------------------------------------------------------

		setPropertyDataSource(dtoBI.getItemProperty(attName));

		// ----------------------------------------------------------------------------

		setReadOnly(readOnly);

	}

}
