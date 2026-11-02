package edu.itm.ProyectoBunueleria.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DatosInvalidos extends RuntimeException { public DatosInvalidos(String mensaje) { super(mensaje); } }
