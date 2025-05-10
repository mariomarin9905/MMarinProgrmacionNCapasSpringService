package com.digis01.MMarinProgrmacionNCapasSpring.DAO;

import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Result;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.UsuarioDireccion;

public interface IUsuarioDAO {  
    
    Result GetAll();

    Result Add(UsuarioDireccion usuarioDireccion);

    Result DireccionesByIdUsuarioJPA(int IdUsuario);

    Result UsuarioByIdJPA(int IdUsuario);

    Result UsuarioUpdateJPA(int IdUsuario, Usuario usuario);

    Result UsuarioDireccionDeleteJPA(int IdUsuario);
    
    Result UsuarioUpdateByEstatusJPA(int IdUsuario, int Estatus);
    
   Result GetAllDinamico(Usuario usuario);
    
}
