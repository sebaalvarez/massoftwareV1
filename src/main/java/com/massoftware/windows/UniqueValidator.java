package com.massoftware.windows;

import com.massoftware.model.Entity;
import com.massoftware.model.EntityId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.AbstractValidator;

public class UniqueValidator extends AbstractValidator<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1435601608143548684L;

	@SuppressWarnings("rawtypes")
	private Class clazz;
	private String mode;
	private String attName;
	private String caption;
	@SuppressWarnings("rawtypes")
	private BeanItem dtoBI;

	@SuppressWarnings("rawtypes")
	public UniqueValidator(Class clazz, String mode, String attName, String caption, BeanItem dtoBI) {
		super("");
		this.clazz = clazz;
		this.mode = mode;
		this.attName = attName;
		this.caption = caption;
		this.dtoBI = dtoBI;
	}
	
	@SuppressWarnings("rawtypes")
	public UniqueValidator(Class clazz, String mode, String attName, BeanItem dtoBI) throws Exception {
		super("");
		this.clazz = clazz;
		this.mode = mode;
		this.attName = attName;
		this.caption = null;
		this.dtoBI = dtoBI;
		
		if (dtoBI.getBean() instanceof Entity) {
			String lbl = ((Entity) dtoBI.getBean()).label(attName);
			if (lbl != null) {
				caption = lbl;
			}
		}

	}

	@Override
	protected boolean isValidValue(Object value) {

		try {						

			if (WindowForm.COPY_MODE.equals(mode)) {
				((EntityId) dtoBI.getBean()).checkUnique(value, attName, caption, true);
			} else {
				((EntityId) dtoBI.getBean()).checkUnique(value, attName, caption, false);
			}

			return true;

		} catch (Exception e) {
			LogAndNotification.print(e);
			this.setErrorMessage(e.getMessage());
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<Object> getType() {

		return clazz;
	}

}
