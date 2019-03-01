package com.massoftware.windows.bloqueos_cliente;

import java.util.Iterator;
import java.util.List;

import com.massoftware.model.BloqueoCliente;
import com.massoftware.model.ClasificacionCliente;
import com.massoftware.model.ClasificacionesClientesFiltro;
import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.SelectorBox;
import com.massoftware.windows.clasificaciones_clientes.WClasificacionesClientes;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Window;

class WCClasificacionClientesSB extends SelectorBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7587377265502188967L;

	@SuppressWarnings("rawtypes")
	public WCClasificacionClientesSB(BeanItem dtoBI) throws Exception {
		super(dtoBI, "clasifCliente", "");
		init();
	}

	private void init() {
//		valueTXT.addBlurListener(e -> {
//			blur();
//		});
		openSelectorBTN.addClickListener(e -> {
			open(true);
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

	@SuppressWarnings({ "rawtypes" })
	void blur() {
		try {

			String value = getValue();

			if (value != null) {

				List items = new ClasificacionCliente().find(buildFiltro(true));

				if (items.size() == 1) {

					setSelectedItem(items.get(0));

				} else if (items.size() == 0) {

					open(false);

				} else {

					open(true);

				}
			} else {

				setSelectedItem(null);
				open(false);

			}
		} catch (Exception e) {
			LogAndNotification.print(e);
		}
	}

	@SuppressWarnings("rawtypes")
	protected void open(boolean filter) {
		try {

			Iterator it = getUI().getWindows().iterator();
			while (it.hasNext()) {
				Window w = (Window) it.next();
				if (w.getClass() == WClasificacionesClientes.class && w.getId() != null && w.getId().equals(uuid)) {
					return;
				}
			}

			WClasificacionesClientes windowPopup = new WClasificacionesClientes(buildFiltro(filter));
			windowPopup.setId(uuid);

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

			valueTXT.setValue(null);

			((BloqueoCliente) dtoBI.getBean()).setClasifCliente((ClasificacionCliente) null);

		} else {

//			valueTXT.setValue(((SeguridadPuerta) item).getNumero() + " - " + ((SeguridadPuerta) item).getNombre());
			
			valueTXT.setValue(item.toString());

			((BloqueoCliente) dtoBI.getBean()).setClasifCliente((ClasificacionCliente) item);

		}

	}

	private ClasificacionesClientesFiltro buildFiltro(boolean filter) {

//		String value = getValue();

		ClasificacionesClientesFiltro filtro = new ClasificacionesClientesFiltro();

		// if (filter) {
		//
		// if (value != null && value.contains("-")) {
		//
		// String value1 = null;
		// String value2 = null;
		//
		// value1 = value.split("-")[0].trim();
		// value2 = value.split("-")[1].trim();
		//
		// try {
		//
		// filtro.setNumero(new Integer(value1));
		// filtro.setNombre(value2);
		//
		// } catch (NumberFormatException e) {
		//
		// filtro.setNumero(null);
		// filtro.setNombre(value2);
		// }
		//
		// } else if (value != null) {
		//
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
		//
		// }
		//
		// }

		return filtro;

	}

}
