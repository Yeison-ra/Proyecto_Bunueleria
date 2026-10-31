package edu.itm.ProyectoBunueleria.services;

import edu.itm.ProyectoBunueleria.jpaEntities.Producto;
import edu.itm.ProyectoBunueleria.jpaRepositories.ProductoJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductosServiceTest {

    @Mock
    private ProductoJpaRepository repository;

    @InjectMocks
    private ProductosService service;

    private Producto bunuelio;

    @BeforeEach
    void setUp() {
        bunuelio = Producto.builder()
                .idProducto(1)
                .nombre("Buñuelo")
                .descripcion("Buñuelo tradicional")
                .idCategoria(1)
                .estado("ACTIVO")
                .costo(1200F)
                .precioVenta(2500F)
                .stockMinimo(10)
                .build();
    }

    @Test
    void cuandoListoProductos_regresaLista() {
        when(repository.findAll()).thenReturn(List.of(bunuelio));

        List<Producto> result = service.getProductos();

        assertEquals(1, result.size());
        assertEquals("Buñuelo", result.get(0).getNombre());
        verify(repository).findAll();
    }

    @Test
    void cuandoConsultoProductoExistente_regresaProducto() {
        when(repository.findById(1)).thenReturn(Optional.of(bunuelio));

        Producto result = service.getProducto(1);

        assertNotNull(result);
        assertEquals(1, result.getIdProducto());
    }

    @Test
    void cuandoConsultoProductoInexistente_regresaNull() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        assertNull(service.getProducto(999));
    }

    @Test
    void cuandoDesactivoProducto_cambiaEstado() {
        when(repository.findById(1)).thenReturn(Optional.of(bunuelio));
        when(repository.save(bunuelio)).thenReturn(bunuelio);

        Producto result = service.desactivarProducto(1);

        assertNotNull(result);
        assertEquals("INACTIVO", result.getEstado());
        verify(repository).save(bunuelio);
    }
}
