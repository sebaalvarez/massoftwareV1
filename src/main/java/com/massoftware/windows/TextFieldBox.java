package com.massoftware.windows;

import java.util.Collection;

import com.vaadin.data.Validatable;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

public class TextFieldBox extends HorizontalLayout implements Validatable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2869082571369904793L;

	public TextFieldEntity valueTXT;
	public Button removeFilterBTN;

	@SuppressWarnings("rawtypes")
	public TextFieldBox(WindowListado window, BeanItem dtoBI, String attName) throws Exception {
		init(window, dtoBI, attName, null);
	}

	@SuppressWarnings("rawtypes")
	public TextFieldBox(WindowListado window, BeanItem dtoBI, String attName, String inputPrompt) throws Exception {
		init(window, dtoBI, attName, inputPrompt);
	}

	@SuppressWarnings("rawtypes")
	private void init(WindowListado window, BeanItem dtoBI, String attName, String inputPrompt) throws Exception {

		this.setSpacing(false);

		// ----------------------------------------------

		valueTXT = new TextFieldEntity(dtoBI, attName, null);

		if (inputPrompt == null && dtoBI.getItemProperty(attName).getType() == Integer.class
				|| dtoBI.getItemProperty(attName).getType() == Long.class) {

			valueTXT.setInputPrompt(UtilUI.buildWinFilterTXTInputPromptList(UtilUI.EQUALS));

		} else if (inputPrompt == null) {

			valueTXT.setInputPrompt(UtilUI.buildWinFilterTXTInputPromptList(UtilUI.CONTAINS_WORDS_AND));

		} else {
			valueTXT.setInputPrompt(UtilUI.buildWinFilterTXTInputPromptList(inputPrompt));
		}

		valueTXT.setDescription(valueTXT.getInputPrompt());

		this.addComponent(valueTXT);

		// ----------------------------------------------

		removeFilterBTN = new Button();
		removeFilterBTN.addStyleName("borderless tiny");
		removeFilterBTN.setIcon(FontAwesome.TIMES);
		removeFilterBTN.setDescription("Quitar filtro " + valueTXT.getCaption() + ".");

		this.addComponent(removeFilterBTN);
		this.setComponentAlignment(removeFilterBTN, Alignment.BOTTOM_LEFT);

		removeFilterBTN.addClickListener(e -> {
			try {
				valueTXT.setValue(null);
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

		valueTXT.addBlurListener(e -> {
			try {
				window.loadDataResetPaged();
			} catch (Exception ex) {
				LogAndNotification.print(ex);
			}
		});

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
