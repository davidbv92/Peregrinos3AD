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
	},REGISTRO_PARADA{

		@Override
		public String getTitle() {
			return getStringFromResourceBundle("registroParada.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Registro_Parada.fxml";
		}
		
<<<<<<< HEAD
=======
	},DATOS_PARADA{

		@Override
		public String getTitle() {
			return getStringFromResourceBundle("datosParada.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/DatosParada.fxml";
		}
		
>>>>>>> 2a9e288fa9e52d479f8307f125b075f77c9a2b91
	};

	public abstract String getTitle();

	public abstract String getFxmlFile();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}
}
