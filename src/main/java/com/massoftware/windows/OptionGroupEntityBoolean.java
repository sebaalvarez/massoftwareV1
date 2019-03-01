package com.massoftware.windows;

import com.massoftware.model.Entity;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.themes.ValoTheme;

public class OptionGroupEntityBoolean extends OptionGroup {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7741977244792585335L;

	@SuppressWarnings("rawtypes")
	public OptionGroupEntityBoolean(WindowListado window, BeanItem dtoBI, String attName, String labelAll, String labelTrue, String labelFalse,
			boolean horizontal, int value) throws Exception {

		String label = ((Entity) dtoBI.getBean()).label(attName);
		boolean required = ((Entity) dtoBI.getBean()).required(attName);
		boolean readOnly = ((Entity) dtoBI.getBean()).readOnly(attName);

		// ----------------------------------------------

		addStyleName(ValoTheme.OPTIONGROUP_SMALL);

		// ----------------------------------------------

		setCaption(label);

		setRequiredError("El campo '" + label + "' es requerido. Es decir no debe estar vacio.");
		setRequired(required);

		// ----------------------------------------------

		addItem(0);
		addItem(1);
		addItem(2);

		setItemCaption(0, labelAll);
		setItemCaption(1, labelTrue);
		setItemCaption(2, labelFalse);

		// ----------------------------------------------

		if (horizontal) {
			addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
		}

		// ----------------------------------------------

		setPropertyDataSource(dtoBI.getItemProperty(attName));

		// ----------------------------------------------

		setValue(value);

		// ----------------------------------------------

		setReadOnly(readOnly);

		// ----------------------------------------------
		
		addValueChangeListener(e -> {
			try {
				window.loadDataResetPaged();
			} catch (Exception ex) {
				LogAndNotification.print(ex);
			}
		});
		
		// ----------------------------------------------

	}

}
