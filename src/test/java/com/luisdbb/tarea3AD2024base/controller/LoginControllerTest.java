package com.luisdbb.tarea3AD2024base.controller;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.User;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.ServicioService;
import com.luisdbb.tarea3AD2024base.services.UserService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private UserService userService;

    @Mock
    private PeregrinoService peregrinoService;

    @Mock
    private ParadaService paradaService;

    @Mock
    private ServicioService servicioService;

    @Mock
    private StageManager stageManager;

    @Mock
    private TextField txtUsuario;

    @Mock
    private PasswordField txtPassword;

    @Mock
    private TextField txtPasswordVisible;

    @Mock
    private FlowPane panelEncriptado;

    @Mock
    private FlowPane panelDesencriptado;

    @BeforeAll
    public static void initJfx() {
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        when(txtUsuario.getText()).thenReturn("admin");
        when(txtPassword.getText()).thenReturn("admin");
        when(txtPasswordVisible.getText()).thenReturn("admin");
    }

    @Test
    void testLoginCorrectoAdministrador() {
        // Simula la entrada del administrador
        when(txtUsuario.getText()).thenReturn("admin");
        when(txtPassword.getText()).thenReturn("admin");

        Platform.runLater(() -> {
            loginController.onIniciarSesion();
        });
        verify(stageManager, times(1)).switchScene(FxmlView.VENTANA_ADMIN);
    }

    @Test
    void testLoginCorrectoPeregrino() {
        // Simula la entrada del usuario peregrino
        User usuario = new User();
        usuario.setUsuario("peregrino");
        usuario.setPassword("peregrino");
        usuario.setEmail("peregrino@peregrino.com");
        usuario.setRol("peregrino");

        when(userService.authenticate("peregrino", "peregrino")).thenReturn(true);
        when(userService.findByUsuario("peregrino")).thenReturn(usuario);

        when(txtUsuario.getText()).thenReturn("peregrino");
        when(txtPassword.getText()).thenReturn("peregrino");

        Platform.runLater(() -> {
            loginController.onIniciarSesion();
        });

        verify(stageManager, times(1)).switchScene(FxmlView.VENTANA_PEREGRINO);
    }

    @Test
    void testLoginCorrectoParada() {
        // Crear un usuario "parada"
        User usuario = new User();
        usuario.setUsuario("parada");
        usuario.setPassword("parada");
        usuario.setEmail("parada@parada.com");
        usuario.setRol("parada");

        when(userService.authenticate("parada", "parada")).thenReturn(true);
        when(userService.findByUsuario("parada")).thenReturn(usuario);

        Platform.runLater(() -> {
            loginController.onIniciarSesion();
        });

        verify(stageManager, times(1)).switchScene(FxmlView.VENTANA_PARADA);
    }

    @Test
    void testLoginUsuarioInvalido() {
        // Simula la entrada con un usuario incorrecto
        when(txtUsuario.getText()).thenReturn("admin2");
        when(txtPassword.getText()).thenReturn("admin2");
        when(userService.authenticate("admin2", "admin2")).thenReturn(false);
        Platform.runLater(() -> {
            loginController.onIniciarSesion();
        });
        verify(stageManager, never()).switchScene(FxmlView.VENTANA_PEREGRINO);
    }

    @Test
    void testLoginConContraseñaIncorrecta() {
        // Simula la entrada con una contraseña incorrecta
        when(txtUsuario.getText()).thenReturn("admin");
        when(txtPassword.getText()).thenReturn("wrongPassword");
        when(userService.authenticate("admin", "wrongPassword")).thenReturn(false);

        Platform.runLater(() -> {
            loginController.onIniciarSesion();
        });
        verify(stageManager, never()).switchScene(FxmlView.VENTANA_PEREGRINO);
    }

    @Test
    void testLoginConUsuarioIncorrecto() {
        // Simula la entrada con un usuario no registrado
        when(txtUsuario.getText()).thenReturn("nonexistentUser");
        when(txtPassword.getText()).thenReturn("password");
        when(userService.authenticate("nonexistentUser", "password")).thenReturn(false);

        Platform.runLater(() -> {
            loginController.onIniciarSesion();
        });
        verify(stageManager, never()).switchScene(FxmlView.VENTANA_PEREGRINO);
    }
}

