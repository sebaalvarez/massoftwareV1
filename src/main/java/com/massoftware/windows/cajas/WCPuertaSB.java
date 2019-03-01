package com.massoftware.windows.cajas;

import java.util.List;

import com.massoftware.model.SeguridadPuerta;
import com.massoftware.model.SeguridadPuertasFiltro;
import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.SelectorBox;
import com.massoftware.windows.seguridad_puertas.WSeguridadPuertas;

class WCPuertaSB extends SelectorBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7587377265502188967L;

	private WCaja window;

	public WCPuertaSB(WCaja window) throws Exception {
		super(window.getItemBIC(), "seguridadPuerta", "");

		this.window = window;

//		valueTXT.addBlurListener(e -> {
//			blur();
//		});
		openSelectorBTN.addClickListener(e -> {
			open();
		});
		removeFilterBTN.addClickListener(e -> {
			try {

				valueTXT.setValue(null);
				setSelectedItem(null);

			} catch (Exception ex) {
				LogAndNotification.print(ex);
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	private void blur() {
		try {

			String value = getValue();

			if (value != null) {

				SeguridadPuertasFiltro filtro = new SeguridadPuertasFiltro();

				// try {
				//
				// filtro.setNumero(new Integer(value));
				// filtro.setNombre(null);
				//
				// } catch (NumberFormatException e) {
				//
				// filtro.setNumero(null);
				// filtro.setNombre(value);
				// }

				List items = new SeguridadPuerta().find(filtro);

				if (items.size() == 1) {

					setSelectedItem(items.get(0));

				} else {

					open();

				}
			} else {

				setSelectedItem(null);

			}
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	protected void open() {
		try {

			SeguridadPuertasFiltro filtro = new SeguridadPuertasFiltro();
			// filtro.setNombre(this.getValue());

			WSeguridadPuertas windowPopup = new WSeguridadPuertas(filtro);

			windowPopup.addCloseListener(e -> {
				try {

					setSelectedItem(windowPopup.itemsGRD.getSelectedRow());

				} catch (Exception ex) {
					LogAndNotification.print(ex);
				}
			});

			windowPopup.seleccionarBTN.addClickListener(e -> {
				try {

					if (windowPopup.itemsGRD.getSelectedRow() != null) {

						setSelectedItem(windowPopup.itemsGRD.getSelectedRow());
						
						windowPopup.close();
						
					}

				} catch (Exception ex) {
					LogAndNotification.print(ex);
				}
			});

			getUI().addWindow(windowPopup);

		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	public void setSelectedItem(Object item) {

		if (item == null) {
			item = new SeguridadPuerta();
		}

		valueTXT.setValue(item.toString());

		window.getItemBIC().getBean().setSeguridadPuerta((SeguridadPuerta) item);

		// window.loadDataResetPaged();

	}

}
