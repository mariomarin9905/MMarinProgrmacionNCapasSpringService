package com.digis01.MMarinProgrmacionNCapasSpring.DAO;

import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Colonia;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Estado;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Municipio;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Pais;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaDAOImplementation implements IColoniaDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result BydIdMunicipio(int IdMunicipio) {
        Result result = new Result();
        try {
            TypedQuery<Colonia> queryColonia = this.entityManager.createQuery("FROM Colonia WHERE Municipio.IdMunicipio = :idmunicipio", Colonia.class);
            queryColonia.setParameter("idmunicipio", IdMunicipio);
            //Valida si esta vacia si es asi mandar null si no; mandar lista
             result.object= queryColonia.getResultList().isEmpty() ? null : queryColonia.getResultList() ;         
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
            result.objects = null;
        }
        return result;
    }

    @Override
    public Result ByCodigoPostal(String CodigoPostal) {
        Result result = new Result();
        try {
            TypedQuery<Colonia> queryColonia = this.entityManager.createQuery("FROM Colonia WHERE CodigoPostal = :vcodigopostal", Colonia.class);
            queryColonia.setParameter("vcodigopostal", CodigoPostal);
            result.object = queryColonia.getResultList().isEmpty() ? null : queryColonia.getResultList();            
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
            result.objects = null;
        }
        return result;
    }

}
