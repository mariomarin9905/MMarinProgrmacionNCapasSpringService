
package com.digis01.MMarinProgrmacionNCapasSpring.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Estado {
    @Id
    @Column(name = "idestado")
    @GeneratedValue(strategy =GenerationType.IDENTITY )
    private int IdEstado;
    @Column(name  = "nombre")
    private String Nombre;
    @JoinColumn(name = "idpais")
    @ManyToOne
    public Pais Pais;

    public int getIdEstado() {
        return IdEstado;
    }

    public void setIdEstado(int IdEstado) {
        this.IdEstado = IdEstado;
    }
    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
}
