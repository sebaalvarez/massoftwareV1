package com.massoftware.windows;

import com.massoftware.model.Entity;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.themes.ValoTheme;

public class CheckBoxEntity extends CheckBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 570038073613213259L;

	@SuppressWarnings("rawtypes")
	public CheckBoxEntity(BeanItem dtoBI, String attName) throws Exception {

		String label = ((Entity) dtoBI.getBean()).label(attName);
		boolean readOnly = ((Entity) dtoBI.getBean()).readOnly(attName);

		// ----------------------------------------------------------------------------

		addStyleName(ValoTheme.CHECKBOX_SMALL);
		setWidthUndefined();
		setHeightUndefined();
		setVisible(true);
		setEnabled(true);
		setReadOnly(false);
		setImmediate(true);

		// ----------------------------------------------------------------------------

		setCaption(label);

		// ----------------------------------------------------------------------------
		setPropertyDataSource(dtoBI.getItemProperty(attName));

		// ----------------------------------------------------------------------------
		setReadOnly(readOnly);
	}

}
