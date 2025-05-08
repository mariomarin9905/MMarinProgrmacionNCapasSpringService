package com.digis01.MMarinProgrmacionNCapasSpring.DAO;

import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Colonia;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Estado;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Municipio;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Pais;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Result;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionDAOImplementation implements IDireccion {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Override
    public Result Delete(int IdDireccion) {
        Result result = new Result();
        try {
            Direccion direccion = this.entityManager.find(Direccion.class, IdDireccion);
            if (direccion != null) {
                this.entityManager.remove(direccion);
                result.correct = true;
            } else {
                result.correct = false;
            }
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Override
    public Result GetById(int IdDireccion) {
        Result result = new Result();
        try {
            Direccion direccionJPA = this.entityManager.find(Direccion.class, IdDireccion);
            if (direccionJPA != null) {
                result.object = direccionJPA;
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "Usuario no existe";
            }

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Transactional
    @Override
    public Result Add(Direccion direccion) {
        Result result = new Result();
        try {
            
            this.entityManager.persist(direccion);
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

    @Transactional
    @Override
    public Result Update(int IdDireccion, Direccion direccion) {
        Result result = new Result();
        try {
            
            Direccion direccionJPA = this.entityManager.find(Direccion.class, IdDireccion);
            if ((direccionJPA != null) && (direccionJPA.getIdDireccion() == IdDireccion) && 
                    (direccionJPA.Usuario.getIdUsuario() == direccion.Usuario.getIdUsuario())) {
                direccionJPA.setIdDireccion(IdDireccion);
                direccionJPA.setCalle(direccion.getCalle());
                direccionJPA.setNumeroExterior(direccion.getNumeroExterior());
                direccionJPA.setNumeroInterior(direccion.getNumeroInterior());
                direccionJPA.Colonia = new com.digis01.MMarinProgrmacionNCapasSpring.JPA.Colonia();
                direccionJPA.Colonia.setIdColonia(direccion.Colonia.getIdColonia());
                this.entityManager.merge(direccionJPA);
                result.correct = true;
            } else {
                result.correct = false;
            }

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return result;
    }

}
