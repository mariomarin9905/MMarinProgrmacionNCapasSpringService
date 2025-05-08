
package com.digis01.MMarinProgrmacionNCapasSpring.DAO;

import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Result;


public interface IColoniaDAO {
        
    Result BydIdMunicipio(int IdMunicipio);
    Result ByCodigoPostal(String CodigoPostal);
    
}
