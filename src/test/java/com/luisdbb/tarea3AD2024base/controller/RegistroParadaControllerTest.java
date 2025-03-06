package com.luisdbb.tarea3AD2024base.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.services.VisitaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RegistroParadaControllerTest {

	@InjectMocks
    private RegistroParadaController registroParadaController;

    @Mock
    private StageManager stageManager;

    @Mock
    private ParadaService paradaService;
    
    @Mock
    private MiAlerta miAlerta;

    @Mock
    private UserService userService;

    @Mock
    private TextField txtNombreParada;

    @Mock
    private TextField txtUsuario;

    @Mock
    private PasswordField txtPassword;

    @Mock
    private PasswordField txtPassword2;

    @Mock
    private TextField txtCorreo;

    @Mock
    private TextField txtRegion;

    @Mock
    private TextField txtResponsable;

    @Mock
    private Button btnRegistrar;

    @BeforeAll
    public static void initJfx() {
        Platform.startup(() -> {
        });
    }
    
    @BeforeEach
    void setUp() {
        when(txtResponsable.getText()).thenReturn("Juan Perez");
        when(txtUsuario.getText()).thenReturn("juanperez");
        when(txtPassword.getText()).thenReturn("password123");
        when(txtPassword2.getText()).thenReturn("password123");
        when(txtCorreo.getText()).thenReturn("juan.perez@example.com");
        when(txtRegion.getText()).thenReturn("a");
        when(txtNombreParada.getText()).thenReturn("Asturias");
        
        Platform.runLater(() -> {
        	 when(MiAlerta.showConfirmationAlert(anyString(), anyString())).thenReturn(true); 
        });
    }
    
    @DisplayName("TC-001 - Registro exitoso con datos válidos")
    @Test
    void testOnRegistrarCamposValidos() throws InterruptedException {
    	CountDownLatch latch = new CountDownLatch(1);
    	
        Platform.runLater(() -> {
        	doNothing().when(miAlerta);
    		MiAlerta.showWarningAlert(any(String.class));
            registroParadaController.onRegistrar();
            latch.countDown();
        });
        
        latch.await(5, TimeUnit.SECONDS);
        verify(paradaService, times(1)).save(any(Parada.class));
        verify(stageManager, times(1)).switchScene(FxmlView.VENTANA_ADMIN);
    }
    
    
    @DisplayName("TC-002 - Registro cancelado por el usuario")
    @Test
    void testOnRegistrarCanceladoPorUsuario() {
        Platform.runLater(() -> {
        	when(MiAlerta.showConfirmationAlert(anyString(), anyString())).thenReturn(false);
            registroParadaController.onRegistrar();
        });
        verify(paradaService, never()).save(any(Parada.class));
        verify(stageManager, never()).switchScene(FxmlView.VENTANA_ADMIN);
    }
    
    //casos para nombres invalidos
    // Caso Parametrizado para validar el responsable en sus diferentes posibles fallos
    @ParameterizedTest
    @DisplayName("TC-003 - Registro fallido por responsable inválido")
    @ValueSource(strings = {"", "   ", "Juan123", "Juan Perez con un nombre muy largo que excede los 50 caracteres"})
    void testResponsableInvalido(String nombre) {
        when(txtResponsable.getText()).thenReturn(nombre);
        
        Platform.runLater(() -> {
            registroParadaController.onRegistrar();
            
        });
        
        verify(paradaService, never()).save(any(Parada.class));
        verify(stageManager, never()).switchScene(FxmlView.VENTANA_ADMIN);
    }
    
  //test para usuario incorrecto
    // Caso Parametrizado para validar el usuario en sus diferentes posibles fallos
    @ParameterizedTest
    @DisplayName("TC-004 - Registro fallido por usuario inválido")
    @ValueSource(strings = {"", "   ", "usuario123", "usuarioextremadamentelargoquenoesvalidoenunusuarioaqui"})
    void testUsuarioInvalido(String usuario) {
        when(txtUsuario.getText()).thenReturn(usuario);
        Platform.runLater(() -> {
            registroParadaController.onRegistrar();
        });
        verify(paradaService, never()).save(any(Parada.class));
        verify(stageManager, never()).switchScene(FxmlView.VENTANA_ADMIN);
    }
    
    //test usuario ya registrado
    @DisplayName("TC-005 - Registro fallido por usuario ya existente")
    @Test
    void testUsuarioExistente() {
    	when(txtUsuario.getText()).thenReturn("usuario");
    	User usuario=new User();
    	usuario.setUsuario("usuario");
    	usuario.setPassword("usuario");
    	usuario.setEmail("usuario@usuario.com");
    	usuario.setRol("peregrino");
    	userService.save(usuario);
    	
        Platform.runLater(() -> {
        	when(MiAlerta.showConfirmationAlert(anyString(), anyString())).thenReturn(false);
            registroParadaController.onRegistrar();
        });
        verify(paradaService, never()).save(any(Parada.class));
        verify(stageManager, never()).switchScene(FxmlView.VENTANA_ADMIN);
    }
    
    //test para password incorrecta
    // Caso Parametrizado para validar el password en sus diferentes posibles fallos
    @ParameterizedTest
    @DisplayName("TC-006 - Registro fallido por contraseña inválida")
    @ValueSource(strings = {"", "   "})
    void testPasswordInvalido(String password) {
        when(txtPassword.getText()).thenReturn(password);
        when(txtPassword2.getText()).thenReturn(password);
        Platform.runLater(() -> {
            registroParadaController.onRegistrar();
        });
        verify(paradaService, never()).save(any(Parada.class));
        verify(stageManager, never()).switchScene(FxmlView.VENTANA_ADMIN);
    }
    
    //caso contraseñas no coinciden
    @DisplayName("TC-007 - Registro fallido por contraseñas que no coinciden")
    @Test
    void testOnRegistrarPasswordNoCoinciden() {
    	when(txtPassword.getText()).thenReturn("password");
        when(txtPassword2.getText()).thenReturn("password2");


        Platform.runLater(() -> {
            registroParadaController.onRegistrar();
        });
        verify(paradaService, never()).save(any(Parada.class));
        verify(stageManager, never()).switchScene(FxmlView.VENTANA_ADMIN);
    }

    //test para correo incorrecto
    // Caso Parametrizado para validar el correo en sus diferentes posibles fallos
    @ParameterizedTest
    @DisplayName("TC-008 - Registro fallido por correo inválido")
    @ValueSource(strings = {"", "   ", "correoincorrecto", "correoooooextremadamentelargoquenoesvalidoenunusuarioaqui"})
    void testCorreoInvalido(String usuario) {
        when(txtUsuario.getText()).thenReturn(usuario);
        Platform.runLater(() -> {
            registroParadaController.onRegistrar();
        });
        verify(paradaService, never()).save(any(Parada.class));
        verify(stageManager, never()).switchScene(FxmlView.VENTANA_ADMIN);
    }
    
    //test correo ya registrado
    @DisplayName("TC-009 - Registro fallido por correo ya registrado")
    @Test
    void testCorreoExistente() {
    	when(txtCorreo.getText()).thenReturn("usuario@usuario.com");
    	User usuario=new User();
    	usuario.setUsuario("usuario");
    	usuario.setPassword("usuario");
    	usuario.setEmail("usuario@usuario.com");
    	usuario.setRol("peregrino");
    	userService.save(usuario);

        Platform.runLater(() -> {
        	when(MiAlerta.showConfirmationAlert(anyString(), anyString())).thenReturn(false);
            registroParadaController.onRegistrar();
        });
        verify(paradaService, never()).save(any(Parada.class));
        verify(stageManager, never()).switchScene(FxmlView.VENTANA_ADMIN);
    }
    
    //test region inválida
    // Caso Parametrizado para validar la region en sus diferentes posibles fallos
    @ParameterizedTest
    @DisplayName("TC-010 - Registro fallido por región inválida")
    @ValueSource(strings = {"", "   ","aa","!","1"})
    void testRegionInvalida(String region) {
        when(txtRegion.getText()).thenReturn(region);
        Platform.runLater(() -> {
            registroParadaController.onRegistrar();
        });
        verify(paradaService, never()).save(any(Parada.class));
        verify(stageManager, never()).switchScene(FxmlView.VENTANA_ADMIN);
    }
    
    //test region inválida
    // Caso Parametrizado para validar la region en sus diferentes posibles fallos
    @ParameterizedTest
    @DisplayName("TC-011 - Registro fallido por nombre inválido")
    @ValueSource(strings = {"", "   ","nombre de la parada extremadamente largo para ser bueno en este mismo caso","parada  1 !"})
    void testNombreInvalido(String nombre) {
        when(txtNombreParada.getText()).thenReturn(nombre);
        Platform.runLater(() -> {
            registroParadaController.onRegistrar();
        });
        verify(paradaService, never()).save(any(Parada.class));
        verify(stageManager, never()).switchScene(FxmlView.VENTANA_ADMIN);
    }
}
