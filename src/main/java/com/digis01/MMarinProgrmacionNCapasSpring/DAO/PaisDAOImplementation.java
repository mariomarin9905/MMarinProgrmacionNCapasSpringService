package com.digis01.MMarinProgrmacionNCapasSpring.DAO;

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
public class PaisDAOImplementation implements IPaisDAO {


    @Autowired
    private EntityManager entityManager;
    
    

    @Override
    public Result GetAll() {
        Result result = new Result();
        try {
            TypedQuery<Pais> queryPais = this.entityManager.createQuery("FROM Pais", Pais.class);
            result.object = queryPais.getResultList().isEmpty() ? null : queryPais.getResultList();            
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex =e;
        }
        return result;
    }

}
