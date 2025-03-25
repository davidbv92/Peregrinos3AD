package com.luisdbb.tarea3AD2024base.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.luisdbb.tarea3AD2024base.modelo.*;
import com.luisdbb.tarea3AD2024base.services.*;

import javafx.application.Platform;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class VentanaSelladoControllerTest {

    @Mock
    private ParadaService paradaService;
    
    @Mock
    private PeregrinoService peregrinoService;
    
    @Mock
    private CarnetService carnetService;
    
    @Mock
    private VisitaService visitaService;
    
    @Mock
    private EstanciaService estanciaService;
    
    @InjectMocks
    private VentanaSelladoController ventanaSelladoController;

    private Parada parada;
    private Peregrino peregrino;
    private Carnet carnet;

    @BeforeAll
    public static void initJfx() {
        Platform.startup(() -> {});
    }
    
    @BeforeEach
    void setUp() {
        // Crear objetos de prueba
        parada = new Parada();
        parada.setId(1L);
        
        peregrino = new Peregrino();
        peregrino.setId(1L);
        User usuario = new User();
        usuario.setUsuario("peregrino1");
        usuario.setEmail("peregrino1@email.com");
        peregrino.setUsuario(usuario);
        
        carnet = new Carnet();
        carnet.setPeregrino(peregrino);
        carnet.setDistancia(10.0);
        
        // Simular respuestas de los servicios
        when(paradaService.find(anyLong())).thenReturn(parada);
        when(peregrinoService.find(anyLong())).thenReturn(peregrino);
        when(carnetService.findByPeregrino_Id(anyLong())).thenReturn(carnet);
        when(estanciaService.existsByPeregrinoAndParadaAndFecha(any(Peregrino.class), any(Parada.class), any(LocalDate.class)))
            .thenReturn(false);
        when(visitaService.existsByPeregrinoAndParadaAndFecha(any(Peregrino.class), any(Parada.class), any(LocalDate.class)))
            .thenReturn(false);
    }

    @Test
    void testPuedeSellar_CuandoNoHaSelladoDebeDevolverTrue() {
        boolean resultado = ventanaSelladoController.puedeSellar();
        assertTrue(resultado, "El peregrino debería poder sellar si no ha visitado la parada hoy.");
    }

    @Test
    void testPuedeSellar_CuandoYaHaSelladoDebeDevolverFalse() {
        when(visitaService.existsByPeregrinoAndParadaAndFecha(peregrino, parada, LocalDate.now()))
            .thenReturn(true);

        boolean resultado = ventanaSelladoController.puedeSellar();
        assertFalse(resultado, "El peregrino no debería poder sellar si ya ha visitado la parada hoy.");
    }

    @Test
    void testPuedeEstanciar_CuandoNoHayEstanciaDebeDevolverTrue() {
        boolean resultado = ventanaSelladoController.puedeEstanciar();
        assertTrue(resultado, "El peregrino debería poder estanciarse si no tiene una estancia hoy.");
    }

    @Test
    void testPuedeEstanciar_CuandoYaTieneEstanciaDebeDevolverFalse() {
        when(estanciaService.existsByPeregrinoAndParadaAndFecha(peregrino, parada, LocalDate.now()))
            .thenReturn(true);

        boolean resultado = ventanaSelladoController.puedeEstanciar();
        assertFalse(resultado, "El peregrino no debería poder estanciarse si ya tiene una estancia hoy.");
    }

    @Test
    void testDatosValidos_CuandoPeregrinoEsNuloDebeDevolverFalse() {
        ventanaSelladoController.peregrino = null;
        boolean resultado = ventanaSelladoController.datosValidos();
        assertFalse(resultado, "No debería permitir sellar si el peregrino es nulo.");
    }

    @Test
    void testDatosValidos_CuandoSeleccionesSonInvalidasDebeDevolverFalse() {
        ventanaSelladoController.peregrino = peregrino;
        boolean resultado = ventanaSelladoController.seleccionesValidas();
        assertFalse(resultado, "No debería permitir sellar si las selecciones de estancia y VIP no son válidas.");
    }
}
