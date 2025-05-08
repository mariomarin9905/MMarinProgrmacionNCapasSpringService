package com.digis01.MMarinProgrmacionNCapasSpring.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private int IdUsuario;
    @Column(name = "username")
    @NotEmpty(message = "Incorrecto UserName vacio")
    @Size(min = 4, max = 50, message = "caracteres entre 4 y 50")
    private String UserName;
    @Column(name = "nombre")
    @NotEmpty(message = "Incorrecto Nombre vacio")
    @Pattern(regexp = "^([a-zA-ZÀ-ÿ\\u00f1\\u00d1]+[,.]?[ ]?|[a-zA-ZÀ-ÿ\\u00f1\\u00d1]+['-]?)+$", message = "Incorrecto solo letras ")
    private String Nombre;
    @Column(name = "apellidopaterno")
    private String ApellidoPaterno;
    @Column(name = "apellidomaterno")
    private String ApellidoMaterno;
    @Column(name = "email")
    private String Email;
    @Column(name = "password")
    private String Password;
    @Column(name = "fechanacimiento")
    private Date FechaNacimiento;
    @Column(name = "sexo")
    private String Sexo;
    @Column(name = "telefono")
    private String Telefono;
    //@Pattern(regexp = "", message = "Numero de Celular incorrecto")
    @Column(name = "celular")
    private String Celular;
    @Column(name = "curp")
    private String CURP;

    @JoinColumn(name = "idrol")
    @OneToOne
    public Rol Rol;
    @Column(name = "imagen")
    private String Imagen;
    @Column(name = "estatus")
    private int Estatus;

    public void setEstatus(int Estatus) {
        this.Estatus = Estatus;
    }

    public int getEstatus() {
        return this.Estatus;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;

    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;

    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getUserName() {
        return this.UserName;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getEmail() {
        return this.Email;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getPassword() {
        return this.Password;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public Date getFechaNacimiento() {
        return this.FechaNacimiento;
    }

    public int getIdUsuario() {
        return this.IdUsuario;
    }

    public String getNombre() {
        return this.Nombre;

    }

    public String getTelefono() {
        return this.Telefono;
    }

    public void setCelular(String Celular) {
        this.Celular = Celular;
    }

    public String getCelular() {
        return this.Celular;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    public String getCURP() {
        return this.CURP;
    }

}
