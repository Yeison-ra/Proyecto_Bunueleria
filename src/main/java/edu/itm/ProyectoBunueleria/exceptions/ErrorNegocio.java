package edu.itm.ProyectoBunueleria.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.CONFLICT)
public class ErrorNegocio extends RuntimeException { public ErrorNegocio(String mensaje) { super(mensaje); } }
