package com.luisdbb.tarea3AD2024base.controller;

import static org.mockito.Mockito.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RegistroPeregrinoControllerTest {

    @InjectMocks
    private RegistroPeregrinoController registroPeregrinoController;

    @Mock
    private StageManager stageManager;

    @Mock
    private ParadaService paradaService;

    @Mock
    private PeregrinoService peregrinoService;

    @Mock
    private VisitaService visitaService;

    @Mock
    private UserService userService;

    @Mock
    private TextField txtNombre;

    @Mock
    private TextField txtUsuario;

    @Mock
    private PasswordField txtPassword;

    @Mock
    private PasswordField txtPassword2;

    @Mock
    private TextField txtCorreo;

    @Mock
    private ComboBox<String> cbNacionalidad;

    @Mock
    private ComboBox<String> cbParadaInicial;

    @Mock
    private Button btnRegistrar;

    @BeforeAll
    public static void initJfx() {
        Platform.startup(() -> {
        });
    }

    
    @BeforeEach
    void setUp() {
        when(txtNombre.getText()).thenReturn("Juan Pérez");
        when(txtUsuario.getText()).thenReturn("juanperez");
        when(txtPassword.getText()).thenReturn("password123");
        when(txtPassword2.getText()).thenReturn("password123");
        when(txtCorreo.getText()).thenReturn("juan.perez@example.com");
        when(cbNacionalidad.getValue()).thenReturn("España");
        when(cbParadaInicial.getValue()).thenReturn("1: Gijon (a)");

        Parada paradaMock = mock(Parada.class);
        when(paradaMock.getId()).thenReturn(1L);
        when(paradaService.find(1L)).thenReturn(paradaMock);
        
        Platform.runLater(() -> {
        	 when(MiAlerta.showConfirmationAlert(anyString(), anyString())).thenReturn(true);
        	 
        });
        
       
    }

    @Test
    void testOnRegistrarCamposValidos() throws InterruptedException {
    	CountDownLatch latch = new CountDownLatch(1);
        
        Platform.runLater(() -> {
            when(MiAlerta.showConfirmationAlert(anyString(), anyString())).thenReturn(true);
            registroPeregrinoController.onRegistrar();
            latch.countDown();
        });
        
        latch.await(5, TimeUnit.SECONDS);
        verify(peregrinoService, times(1)).save(any(Peregrino.class));
        verify(stageManager, times(1)).switchScene(FxmlView.VENTANA_PEREGRINO);
    }
    
    @Test
    void testOnRegistrarCanceladoPorUsuario() {
        Platform.runLater(() -> {
        	when(MiAlerta.showConfirmationAlert(anyString(), anyString())).thenReturn(false);
            registroPeregrinoController.onRegistrar();
        });
        verify(peregrinoService, never()).save(any(Peregrino.class));
        verify(stageManager, never()).switchScene(FxmlView.VENTANA_PEREGRINO);
    }

    
    //casos para nombres invalidos
    // Caso Parametrizado para validar el nombre en sus diferentes posibles fallos
    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "Juan123", "Juan Pérez con un nombre muy largo que excede los 50 caracteres"})
    void testNombreInvalido(String nombre) {
        when(txtNombre.getText()).thenReturn(nombre);
        
        Platform.runLater(() -> {
            registroPeregrinoController.onRegistrar();
            
        });
        
        verify(peregrinoService, never()).save(any(Peregrino.class));
        verify(stageManager, never()).switchScene(FxmlView.VENTANA_PEREGRINO);
    }

    
    //test para usuario incorrecto
    // Caso Parametrizado para validar el usuario en sus diferentes posibles fallos
    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "usuario123", "usuarioextremadamentelargoquenoesvalidoenunusuarioaqui"})
    void testUsuarioInvalido(String usuario) {
        when(txtUsuario.getText()).thenReturn(usuario);
        Platform.runLater(() -> {
            registroPeregrinoController.onRegistrar();
        });
        verify(peregrinoService, never()).save(any(Peregrino.class));
        verify(stageManager, never()).switchScene(FxmlView.VENTANA_PEREGRINO);
    }
    
  //test usuario ya registrado
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
            registroPeregrinoController.onRegistrar();
        });
        verify(peregrinoService, never()).save(any(Peregrino.class));
        verify(stageManager, never()).switchScene(FxmlView.VENTANA_PEREGRINO);
    }
    
    //test para password incorrecta
    // Caso Parametrizado para validar el password en sus diferentes posibles fallos
    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void testPasswordInvalido(String password) {
        when(txtPassword.getText()).thenReturn(password);
        when(txtPassword2.getText()).thenReturn(password);
        Platform.runLater(() -> {
            registroPeregrinoController.onRegistrar();
        });
        verify(peregrinoService, never()).save(any(Peregrino.class));
        verify(stageManager, never()).switchScene(FxmlView.VENTANA_PEREGRINO);
    }
    
    //caso contraseñas no coinciden
    @Test
    void testOnRegistrarPasswordNoCoinciden() {
    	when(txtPassword.getText()).thenReturn("password");
        when(txtPassword2.getText()).thenReturn("password2");


        Platform.runLater(() -> {
            registroPeregrinoController.onRegistrar();
        });
        verify(peregrinoService, never()).save(any(Peregrino.class));
        verify(stageManager, never()).switchScene(FxmlView.VENTANA_PEREGRINO);
    }

    //test para correo incorrecto
    // Caso Parametrizado para validar el correo en sus diferentes posibles fallos
    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "correoincorrecto", "usuarioextremadamentelargoquenoesvalidoenunusuarioaqui"})
    void testCorreoInvalido(String usuario) {
        when(txtUsuario.getText()).thenReturn(usuario);
        Platform.runLater(() -> {
            registroPeregrinoController.onRegistrar();
        });
        verify(peregrinoService, never()).save(any(Peregrino.class));
        verify(stageManager, never()).switchScene(FxmlView.VENTANA_PEREGRINO);
    }
    
    //test correo ya registrado
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
            registroPeregrinoController.onRegistrar();
        });
        verify(peregrinoService, never()).save(any(Peregrino.class));
        verify(stageManager, never()).switchScene(FxmlView.VENTANA_PEREGRINO);
    }

    
}
