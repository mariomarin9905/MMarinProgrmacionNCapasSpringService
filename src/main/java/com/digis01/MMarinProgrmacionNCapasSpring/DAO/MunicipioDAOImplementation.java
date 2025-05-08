package com.digis01.MMarinProgrmacionNCapasSpring.DAO;

import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Municipio;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

@Repository
public class MunicipioDAOImplementation implements IMunicipio {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result ByIdEstado(int IdEstado) {
        Result result = new Result();
        try {
            TypedQuery<Municipio> queryMunicipio = this.entityManager.createQuery("FROM Municipio WHERE Estado.IdEstado = :idestado", Municipio.class);
            queryMunicipio.setParameter("idestado", IdEstado);
            result.object = queryMunicipio.getResultList();            
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

}
