package com.luisdbb.tarea3AD2024base.config;

import static org.slf4j.LoggerFactory.getLogger;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;

import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * Manages switching Scenes on the Primary Stage
 */
public class StageManager {

    private static final Logger LOG = getLogger(StageManager.class);
    private final Stage primaryStage;
    private final SpringFXMLLoader springFXMLLoader;

    public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
        this.springFXMLLoader = springFXMLLoader;
        this.primaryStage = stage;
    }

    public void switchScene(final FxmlView view) {
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        show(viewRootNodeHierarchy, view.getTitle());
    }
    
    private void show(final Parent rootnode, String title) {
        Scene scene = prepareScene(rootnode);
        //scene.getStylesheets().add("/styles/Styles.css");
        
        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/concha.png")));
        
      scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
          if (event.getCode() == KeyCode.F1) {
              mostrarAyuda();
          }
      }

		
  });
        
        try {
        	primaryStage.show();
        }catch(Exception exception) {
        	logAndExit ("Unable to show scene for title" + title, exception);
        }
    }
    
	private Scene prepareScene(Parent rootnode){
        Scene scene = primaryStage.getScene();

        if (scene == null) {
            scene = new Scene(rootnode);
        }
        scene.setRoot(rootnode);
        return scene;
    }

    /**
     * Loads the object hierarchy from a FXML document and returns to root node
     * of that hierarchy.
     *
     * @return Parent root node of the FXML document hierarchy
     */
    private Parent loadViewNodeHierarchy(String fxmlFilePath) {
        Parent rootNode = null;
        try {
            rootNode = springFXMLLoader.load(fxmlFilePath);
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
        } catch (Exception exception) {
            logAndExit("Unable to load FXML view" + fxmlFilePath, exception);
        }
        return rootNode;
    }
    
    
    private void logAndExit(String errorMsg, Exception exception) {
        LOG.error(errorMsg, exception, exception.getCause());
        Platform.exit();
    }
    
    public void openModal(final FxmlView view) {
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        showModal(viewRootNodeHierarchy, view.getTitle());
    }

    private void showModal(final Parent rootnode, String title) {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle(title);
        modalStage.setResizable(false);
        Scene scene = new Scene(rootnode);
        modalStage.setScene(scene);
        modalStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/concha.png")));
        modalStage.setX(primaryStage.getX() + 100);
        modalStage.setY(primaryStage.getY() + 100);
        modalStage.showAndWait();
    }
    
    private void mostrarAyuda() {
    	WebView webView=new WebView();
		String url=getClass().getResource("/help/help.html").toExternalForm();
		webView.getEngine().load(url);
		
		Stage helpStage=new Stage();
		helpStage.setTitle("Ayuda PEREGRINAPP");
		
		Scene helpScene=new Scene(webView,600,400);
		
		helpStage.setScene(helpScene);
		
		helpStage.initModality(Modality.APPLICATION_MODAL);
		helpStage.setResizable(true);
		helpStage.show();
	}
    
    /**
     * Método para abrir un PDF en el navegador por defecto.
     * @param ruta ruta al archivo PDF que se desea mostrar.
     */
    public void openPdfInModal(String ruta) {
    	try {
            // Obtener el directorio de trabajo actual
            String workingDirectory = System.getProperty("user.dir");

            // Construir la ruta relativa al archivo PDF
            Path pdfPath = Paths.get(workingDirectory, ruta);

            // Convertir la ruta a una cadena
            String pdfUrl = pdfPath.toString();

            // Detectar el sistema operativo
            String os = System.getProperty("os.name").toLowerCase();
            String comando = "";

            if (os.contains("win")) {
                comando = "rundll32 url.dll,FileProtocolHandler " + pdfUrl;
            } else if (os.contains("mac")) {
                comando = "open " + pdfUrl;
            } else if (os.contains("nix") || os.contains("nux")) {
                comando = "xdg-open " + pdfUrl;
            } else {
                System.out.println("Sistema operativo no soportado para abrir el archivo PDF.");
                return;
            }

            // Ejecutar el comando
            Runtime.getRuntime().exec(comando);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
