package com.massoftware.windows;

import java.util.Collection;
import java.util.List;

import com.vaadin.data.Validatable;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

public class ComboBoxBox extends HorizontalLayout implements Validatable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2869082571369904793L;

	public ComboBoxEntity valueCBX;
	public Button removeFilterBTN;

	@SuppressWarnings("rawtypes")
	public ComboBoxBox(WindowListado window, BeanItem dtoBI, String attName, List options)
			throws Exception {
		init(window, dtoBI, attName, options, null);
	}

	@SuppressWarnings("rawtypes")
	private void init(WindowListado window, BeanItem dtoBI, String attName, List options, Object selectItem)
			throws Exception {

		this.setSpacing(false);

		// ----------------------------------------------

		valueCBX = new ComboBoxEntity(dtoBI, attName, null, options, selectItem);

		valueCBX.setDescription(valueCBX.getInputPrompt());

		this.addComponent(valueCBX);

		// ----------------------------------------------

		removeFilterBTN = new Button();
		removeFilterBTN.addStyleName("borderless tiny");
		removeFilterBTN.setIcon(FontAwesome.TIMES);
		removeFilterBTN.setDescription("Quitar filtro " + valueCBX.getCaption() + ".");

		this.addComponent(removeFilterBTN);
		this.setComponentAlignment(removeFilterBTN, Alignment.BOTTOM_LEFT);

		removeFilterBTN.addClickListener(e -> {
			try {
				valueCBX.setValue(null);
				window.loadDataResetPaged();
			} catch (Exception ex) {
				LogAndNotification.print(ex);
			}
		});

		// valueTXT.addTextChangeListener(e -> {
		// try {
		// valueTXT.setValue(e.getText());
		// window.loadDataResetPaged();
		// } catch (Exception ex) {
		// LogAndNotification.print(ex);
		// }
		// });

		valueCBX.addValueChangeListener(e -> {
			try {
				window.loadDataResetPaged();
			} catch (Exception ex) {
				LogAndNotification.print(ex);
			}
		});

	}

	public void validate() throws Validator.InvalidValueException {
		valueCBX.validate();
	}

	@Override
	public void addValidator(Validator validator) {
		valueCBX.addValidator(validator);

	}

	@Override
	public void removeValidator(Validator validator) {
		valueCBX.removeValidator(validator);

	}

	@Override
	public void removeAllValidators() {
		valueCBX.removeAllValidators();

	}

	@Override
	public Collection<Validator> getValidators() {

		return valueCBX.getValidators();
	}

	@Override
	public boolean isValid() {

		return valueCBX.isValid();
	}

	@Override
	public boolean isInvalidAllowed() {

		return valueCBX.isInvalidAllowed();
	}

	@Override
	public void setInvalidAllowed(boolean invalidValueAllowed) throws UnsupportedOperationException {
		valueCBX.setInvalidAllowed(invalidValueAllowed);

	}

}
