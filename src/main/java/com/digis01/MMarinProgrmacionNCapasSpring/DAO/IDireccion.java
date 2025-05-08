
package com.digis01.MMarinProgrmacionNCapasSpring.DAO;

import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Result;


public interface IDireccion {
   
    //Metodos JPA
    Result Delete(int IdDireccion);      
    Result GetById(int IdDireccion);    
    Result Add(Direccion direccion);
    Result Update(int IdDireccion, Direccion direccion);
}
