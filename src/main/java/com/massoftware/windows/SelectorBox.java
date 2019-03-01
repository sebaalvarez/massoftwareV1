package com.massoftware.windows;

import java.util.Collection;
import java.util.UUID;

import com.massoftware.model.Entity;
import com.vaadin.data.Validatable;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class SelectorBox extends HorizontalLayout implements Validatable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2869082571369904793L;

	public Button openSelectorBTN;
	public TextField valueTXT;
	public Button removeFilterBTN;

	protected String uuid;

	@SuppressWarnings("rawtypes")
	protected BeanItem dtoBI;

	@SuppressWarnings("rawtypes")
	public SelectorBox(BeanItem dtoBI, String attName) throws Exception {
		init(dtoBI, attName, null);
	}

	@SuppressWarnings("rawtypes")
	public SelectorBox(BeanItem dtoBI, String attName, String label2) throws Exception {
		init(dtoBI, attName, label2);
	}

	@SuppressWarnings("rawtypes")
	private void init(BeanItem dtoBI, String attName, String label2) throws Exception {

		uuid = UUID.randomUUID().toString();

		this.dtoBI = dtoBI;

		// HorizontalLayout hl = buildHL();
		this.setWidthUndefined();
		this.setMargin(false);
		this.setSpacing(false);
		// hl.setCaption(label);

		String label = ((Entity) dtoBI.getBean()).label(attName);
		boolean required = ((Entity) dtoBI.getBean()).required(attName);
		label2 = (label2 == null || label2.trim().length() == 0) ? label : label2;
		int columns = (int) ((Entity) dtoBI.getBean()).columns(attName);

		openSelectorBTN = new Button();
		openSelectorBTN.addStyleName("borderless tiny");
		openSelectorBTN.setIcon(FontAwesome.FOLDER_OPEN);
		openSelectorBTN.setDescription("Buscar " + label);

		valueTXT = new TextField();
		valueTXT.setValidationVisible(true);
		valueTXT.setRequiredError("El campo es requerido. Es decir no debe estar vacio.");
		valueTXT.setNullRepresentation("");
		valueTXT.addStyleName(ValoTheme.TEXTFIELD_TINY);
		valueTXT.setColumns(columns);

		// txtValue.setEnabled(false);
		valueTXT.setRequired(required);
		valueTXT.setDescription("Buscar por " + label2);
		valueTXT.setInputPrompt("Buscar por " + label2.toLowerCase());
		// valueTXT.setCaption(label);
		setCaption(label);

		this.addComponent(openSelectorBTN);
		this.setComponentAlignment(openSelectorBTN, Alignment.BOTTOM_LEFT);
		this.addComponent(valueTXT);
		this.setComponentAlignment(valueTXT, Alignment.MIDDLE_LEFT);

		// ----------------------------------------------

		removeFilterBTN = new Button();
		removeFilterBTN.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		removeFilterBTN.addStyleName(ValoTheme.BUTTON_TINY);
		removeFilterBTN.setIcon(FontAwesome.TIMES);
		removeFilterBTN.setDescription("Borrar valor");

		removeFilterBTN.addClickListener(e -> {
			valueTXT.setValue(null);
		});

		this.addComponent(removeFilterBTN);
		this.setComponentAlignment(removeFilterBTN, Alignment.BOTTOM_LEFT);

		this.setComponentAlignment(valueTXT, Alignment.BOTTOM_LEFT);
		this.setComponentAlignment(openSelectorBTN, Alignment.BOTTOM_LEFT);

		// valueTXT.setPropertyDataSource(dtoBI.getItemProperty(attName));

		// this.addShortcutListener(new ShortcutListener("DELETE", KeyCode.DELETE, new
		// int[] {}) {
		//
		// private static final long serialVersionUID = 1L;
		//
		// @Override
		// public void handleAction(Object sender, Object target) {
		// if (target.equals(valueTXT)) {
		// valueTXT.setValue(null);
		// }
		// }
		// });

	}

	protected String getValue() {

		String value = this.valueTXT.getValue();

		if (value != null) {
			value = value.trim();
			if (value.length() == 0) {
				value = null;
			}
		}

		return value;
	}

	public void validate() throws Validator.InvalidValueException {
		valueTXT.validate();
	}

	@Override
	public void addValidator(Validator validator) {
		valueTXT.addValidator(validator);

	}

	@Override
	public void removeValidator(Validator validator) {
		valueTXT.removeValidator(validator);

	}

	@Override
	public void removeAllValidators() {
		valueTXT.removeAllValidators();

	}

	@Override
	public Collection<Validator> getValidators() {

		return valueTXT.getValidators();
	}

	@Override
	public boolean isValid() {

		return valueTXT.isValid();
	}

	@Override
	public boolean isInvalidAllowed() {

		return valueTXT.isInvalidAllowed();
	}

	@Override
	public void setInvalidAllowed(boolean invalidValueAllowed) throws UnsupportedOperationException {
		valueTXT.setInvalidAllowed(invalidValueAllowed);

	}

}
