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

	},DATOS_PARADA{

		@Override
		public String getTitle() {
			return getStringFromResourceBundle("datosParada.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/DatosParada.fxml";
		}
		
	}, VENTANA_SELLADO{
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("ventanaSellado.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Ventana_Sellado.fxml";
		}
	}, EDITAR_DATOS{
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("editarDatos.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Editar_Datos.fxml";
		}
	},REGISTRO_SERVICIO{

		@Override
		public String getTitle() {
			return getStringFromResourceBundle("registroServicio.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Registro_Servicio.fxml";
		}
		
	},DETALLES_ESTANCIA{

		@Override
		public String getTitle() {
			return getStringFromResourceBundle("detallesEstancia.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Detalles_Estancia_2.fxml";
		}
		
	},ENVIOS_REALIZADOS{

		@Override
		public String getTitle() {
			return getStringFromResourceBundle("enviosRealizados.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Envios_Realizados.fxml";
		}
		
	},EDITAR_SERVICIO{

		@Override
		public String getTitle() {
			return getStringFromResourceBundle("editarServicio.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Editar_Servicio.fxml";
		}
		
	},CARNETS_EXPEDIDOS{

		@Override
		public String getTitle() {
			return getStringFromResourceBundle("carnetsExpedidos.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Carnets_Expedidos.fxml";
		}
		
	};

	public abstract String getTitle();

	public abstract String getFxmlFile();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}
}
