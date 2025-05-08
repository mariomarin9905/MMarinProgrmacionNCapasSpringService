
package com.digis01.MMarinProgrmacionNCapasSpring.JPA;

import jakarta.validation.Valid;
import java.util.List;


public class UsuarioDireccion {
    @Valid
    public Usuario Usuario;
    @Valid
    public Direccion Direccion;
    public List<Direccion> Direcciones;
}
