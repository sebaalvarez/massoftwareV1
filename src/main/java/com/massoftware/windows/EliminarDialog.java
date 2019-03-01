package com.massoftware.windows;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class EliminarDialog extends Window implements Button.ClickListener {

	private static final long serialVersionUID = -1143328551246574750L;

	Callback callback;
	Button yes = new Button("Eliminar", this);
	Button no = new Button("Cancelar", this);

	public EliminarDialog(String question, Callback callback) {
		super();

		this.callback = callback;
		
		yes.addStyleName(ValoTheme.BUTTON_DANGER);
		yes.addStyleName(ValoTheme.BUTTON_TINY);
		yes.setIcon(FontAwesome.TRASH);
		
		no.addStyleName(ValoTheme.BUTTON_PRIMARY);
		no.addStyleName(ValoTheme.BUTTON_TINY);
		
		no.focus();		
		
		this.setCaption("Eliminar item");
		this.setImmediate(true);
		// window.setWidth("-1px");
		// window.setHeight("-1px");
		this.setWidthUndefined();
		this.setHeightUndefined();
		this.setClosable(true);
		this.setResizable(false);
		this.setModal(false);
		this.center();
		
		
		setModal(true);
		VerticalLayout content = UtilUI.buildWinContentVertical();

		HorizontalLayout hl = new HorizontalLayout();

		if (question != null) {
			hl.addComponent(new Label("<h3>¿ Esta seguro de eliminar el ítem ?</h3>\n<ul><li><b>" + question + "</b></li></ul>",
				    ContentMode.HTML));
		}

		// -------------------------------------------------------

		HorizontalLayout filaBotoneraHL = new HorizontalLayout();
		filaBotoneraHL.setSpacing(true);

		filaBotoneraHL.addComponents(yes, no);

		content.addComponents(hl, filaBotoneraHL);
		
		content.setComponentAlignment(filaBotoneraHL,
				Alignment.MIDDLE_RIGHT);

		setContent(content);
	}

	public void buttonClick(ClickEvent event) {
		this.close();
		callback.onDialogResult(event.getSource() == yes);
	}

	public interface Callback {

		public void onDialogResult(boolean resultIsYes);
	}

}
