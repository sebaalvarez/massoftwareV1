package com.massoftware.windows;

import java.util.List;

import com.massoftware.model.Entity;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.themes.ValoTheme;

public class OptionGroupEntity extends OptionGroup {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7741977244792585335L;

	@SuppressWarnings("rawtypes")
	public OptionGroupEntity(WindowListado window, BeanItem dtoBI, String attName, List options, boolean horizontal,
			Object selectItem) throws Exception {
		
		init(dtoBI, attName, options, horizontal, selectItem);
	}

	@SuppressWarnings("rawtypes")
	@Deprecated()
	public OptionGroupEntity(WindowForm window, BeanItem dtoBI, String attName, List options, boolean horizontal,
			Object selectItem) throws Exception {
		
		init(dtoBI, attName, options, horizontal, selectItem);
	}
	
	@SuppressWarnings("rawtypes")
	public OptionGroupEntity(BeanItem dtoBI, String attName, List options, boolean horizontal,
			Object selectItem) throws Exception {
		
		init(dtoBI, attName, options, horizontal, selectItem);
	}

	@SuppressWarnings("rawtypes")
	private void init(BeanItem dtoBI, String attName, List options, boolean horizontal, Object selectItem)
			throws Exception {

		String label = ((Entity) dtoBI.getBean()).label(attName);
		String labelError = ((Entity) dtoBI.getBean()).labelError(attName);
		if (labelError == null || labelError.trim().length() == 0) {
			labelError = label;
		}
		boolean required = ((Entity) dtoBI.getBean()).required(attName);
		boolean readOnly = ((Entity) dtoBI.getBean()).readOnly(attName);

		// ----------------------------------------------

		addStyleName(ValoTheme.OPTIONGROUP_SMALL);

		// ----------------------------------------------

		setCaption(label);

		setRequiredError("El campo '" + labelError + "' es requerido. Es decir no debe estar vacio.");
		setRequired(required);

		// ----------------------------------------------

		for (Object option : options) {
			addItem(option);
		}
		// setItemCaption(0, labelAll);

		// ----------------------------------------------

		if (horizontal) {
			addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
		}

		// ----------------------------------------------

		setPropertyDataSource(dtoBI.getItemProperty(attName));

		// ----------------------------------------------

		if (selectItem != null) {
			setValue(selectItem);
		}

		// ----------------------------------------------

		setReadOnly(readOnly);

		// ----------------------------------------------

	}

}
