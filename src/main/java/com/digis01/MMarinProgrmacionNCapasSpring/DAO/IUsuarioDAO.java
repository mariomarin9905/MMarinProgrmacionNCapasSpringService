package com.digis01.MMarinProgrmacionNCapasSpring.DAO;

import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Result;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.UsuarioDireccion;

public interface IUsuarioDAO {  
    
    Result UsuarioGetAllJPA();

    Result Add(UsuarioDireccion usuarioDireccion);

    Result DireccionesByIdUsuarioJPA(int IdUsuario);

    Result UsuarioByIdJPA(int IdUsuario);

    Result UsuarioUpdateJPA(int IdUsuario, Usuario usuario);

    Result UsuarioDireccionDeleteJPA(int IdUsuario);
    
    Result UsuarioUpdateByEstatusJPA(int IdUsuario, int Estatus);
    
   Result UsuarioGetAllDinamicoJPA(Usuario usuario);
    
}
