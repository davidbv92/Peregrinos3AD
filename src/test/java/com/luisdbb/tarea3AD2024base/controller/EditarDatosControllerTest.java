package com.luisdbb.tarea3AD2024base.controller;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.MockitoAnnotations;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class EditarDatosControllerTest {

    @InjectMocks
    private EditarDatosController editarDatosController;

    @Mock
    private StageManager stageManager;

    @Mock
    private PeregrinoService peregrinoService;

    @Mock
    private TextField txtNombre;

    @Mock
    private ComboBox<String> cbNacionalidad;

    @Mock
    private Label lblId, lblNombre, lblNacionalidad, lblUsuario, lblCorreo;

    @Mock
    private Button btnVolver, btnModificar;

    private Peregrino peregrino;

    @BeforeAll
    public static void initJfx() {
        Platform.startup(() -> {});
    }
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        peregrino = new Peregrino();
        peregrino.setId(1L);
        peregrino.setNombre("Juan Perez");
        peregrino.setNacionalidad("España");

        when(peregrinoService.find(Sesion.getInstancia().getId())).thenReturn(peregrino);
        when(txtNombre.getText()).thenReturn("Juan Perez");
        when(cbNacionalidad.getValue()).thenReturn("España");

        Platform.runLater(() -> when(MiAlerta.showConfirmationAlert(anyString(), anyString())).thenReturn(true));
    }

    @DisplayName("TC-001 - Modificación exitosa de datos")
    @Test
    void testModificarDatosExitoso() {
        Platform.runLater(() -> {
            editarDatosController.onModificar();
        });

        verify(peregrinoService, times(1)).save(any(Peregrino.class));
        verify(stageManager, times(1)).switchScene(FxmlView.VENTANA_PEREGRINO);
    }

    @DisplayName("TC-002 - Cancelación de modificación por usuario")
    @Test
    void testModificarCanceladoPorUsuario() {
        Platform.runLater(() -> {
            when(MiAlerta.showConfirmationAlert(anyString(), anyString())).thenReturn(false);
            editarDatosController.onModificar();
        });

        verify(peregrinoService, never()).save(any(Peregrino.class));
        verify(stageManager, never()).switchScene(FxmlView.VENTANA_PEREGRINO);
    }

    @DisplayName("TC-003 - Modificación fallida por nombre inválido")
    @Test
    void testNombreInvalido() {
        when(txtNombre.getText()).thenReturn("");

        Platform.runLater(() -> {
            editarDatosController.onModificar();
        });

        verify(peregrinoService, never()).save(any(Peregrino.class));
        verify(stageManager, never()).switchScene(FxmlView.VENTANA_PEREGRINO);
    }

    @DisplayName("TC-004 - Modificación fallida por nacionalidad inválida")
    @Test
    void testNacionalidadInvalida() {
        when(cbNacionalidad.getValue()).thenReturn("");

        Platform.runLater(() -> {
            editarDatosController.onModificar();
        });

        verify(peregrinoService, never()).save(any(Peregrino.class));
        verify(stageManager, never()).switchScene(FxmlView.VENTANA_PEREGRINO);
    }

    @DisplayName("TC-005 - Salir de edición sin modificar")
    @Test
    void testSalirSinModificar() {
        Platform.runLater(() -> {
            when(MiAlerta.showConfirmationAlert(anyString())).thenReturn(true);
            editarDatosController.onSalir();
        });

        verify(stageManager, times(1)).switchScene(FxmlView.VENTANA_PEREGRINO);
    }
}
