package com.luisdbb.tarea3AD2024base.view;

import java.util.ResourceBundle;

public enum FxmlView {
	USER {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("user.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/User.fxml";
		}
	},
	LOGIN {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("login.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/MiLogin.fxml";
		}
	},REGISTRO_PEREGRINO {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("registroPeregrino.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Registro_Peregrino.fxml";
		}
	},VENTANA_ADMIN {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("ventanaAdmin.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Ventana_Admin.fxml";
		}
	},VENTANA_PEREGRINO {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("ventanaPeregrino.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Ventana_Peregrino.fxml";
		}
	},VENTANA_PARADA {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("ventanaParada.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Ventana_Parada.fxml";
		}
	};

	public abstract String getTitle();

	public abstract String getFxmlFile();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}
}
