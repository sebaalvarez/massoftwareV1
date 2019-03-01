package com.massoftware.windows.cuentas_fondo;

import java.util.List;

import com.massoftware.model.Banco;
import com.massoftware.model.BancosFiltro;
import com.massoftware.windows.LogAndNotification;
import com.massoftware.windows.SelectorBox;
import com.massoftware.windows.bancos.WBancos;

class WCBancoSB extends SelectorBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7587377265502188967L;

	private WCuentasFondo window;

	public WCBancoSB(WCuentasFondo window) throws Exception {
		super(window.filterBI, "banco", "Nº o Nombre");

		this.window = window;

		valueTXT.addBlurListener(e -> {
			blur();
		});
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

	@SuppressWarnings("rawtypes")
	protected void blur() {
		try {

			String value = getValue();

			if (value != null) {

				BancosFiltro filtro = new BancosFiltro();

				try {

					filtro.setNumero(new Integer(value));
					filtro.setNombre(null);

				} catch (NumberFormatException e) {

					filtro.setNumero(null);
					filtro.setNombre(value);
				}

				List items = new Banco().find(filtro);

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

			BancosFiltro filtro = new BancosFiltro();
			filtro.setNombre(this.getValue());

			WBancos windowPopup = new WBancos(filtro);

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

	private void setSelectedItem(Object item) {

		if (item == null) {
			item = new Banco();
		}

		valueTXT.setValue(item.toString());

		window.filterBI.getBean().setBanco((Banco) item);

		window.loadDataResetPaged();

	}

}
