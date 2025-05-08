
package com.digis01.MMarinProgrmacionNCapasSpring.DAO;

import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Estado;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EstadoDAOImplementation implements IEstadoDAO {
    
    @Autowired
    private EntityManager entityManager;

    @Override
    public Result ByIdPais(int IdPais) {
        Result result = new Result();
        try {
            TypedQuery<Estado> queryEstado = this.entityManager.createQuery("FROM Estado WHERE Pais.IdPais = :idpais", Estado.class);
            queryEstado.setParameter("idpais", IdPais);
            result.object = queryEstado.getResultList().isEmpty() ? null : queryEstado.getResultList();           
            result.correct = true;            
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }
    
    
}
