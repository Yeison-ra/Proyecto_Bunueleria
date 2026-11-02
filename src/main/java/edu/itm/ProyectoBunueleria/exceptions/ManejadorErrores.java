package edu.itm.ProyectoBunueleria.exceptions;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class ManejadorErrores {
 @ExceptionHandler(DatosInvalidos.class) public ResponseEntity<Map<String,String>> invalido(DatosInvalidos e) { return ResponseEntity.badRequest().body(Map.of("error",e.getMessage())); }
 @ExceptionHandler(NoEncontrado.class) public ResponseEntity<Map<String,String>> ausente(NoEncontrado e) { return ResponseEntity.status(404).body(Map.of("error",e.getMessage())); }
 @ExceptionHandler(ErrorNegocio.class) public ResponseEntity<Map<String,String>> conflicto(ErrorNegocio e) { return ResponseEntity.status(409).body(Map.of("error",e.getMessage())); }
}
