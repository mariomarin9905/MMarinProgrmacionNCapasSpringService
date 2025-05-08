package com.digis01.MMarinProgrmacionNCapasSpring.DAO;

import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Colonia;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Estado;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Municipio;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Pais;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Result;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Rol;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioDAOImplementation implements IUsuarioDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result UsuarioGetAllJPA() {
        Result result = new Result();
        try {
            TypedQuery<Usuario> queryUsuarios = this.entityManager.createQuery("FROM Usuario U ORDER BY U.IdUsuario", Usuario.class);
            List<Usuario> usuariosJPA = queryUsuarios.getResultList();
            result.objects = new ArrayList();
            for (com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario usuario : usuariosJPA) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Usuario = usuario;
                TypedQuery<Direccion> queryDireccion = this.entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario", Direccion.class);
                queryDireccion.setParameter("idusuario", usuario.getIdUsuario());
                List<Direccion> direccionesJPA = queryDireccion.getResultList();
                usuarioDireccion.Direcciones = direccionesJPA;
                result.objects.add(usuarioDireccion);
            }
            result.correct = true;
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
            result.objects = null;
        }
        return result;
    }

    @Transactional
    @Override
    public Result Add(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        try {
            this.entityManager.persist(usuarioDireccion.Usuario);
            usuarioDireccion.Direccion.Usuario = new Usuario();
            usuarioDireccion.Direccion.Usuario.setIdUsuario(usuarioDireccion.Usuario.getIdUsuario());
            this.entityManager.persist(usuarioDireccion.Direccion);
            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Override
    public Result DireccionesByIdUsuarioJPA(int IdUsuario) {
        Result result = new Result();
        try {
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.Usuario = this.entityManager.find(Usuario.class, IdUsuario);
            if (usuarioDireccion.Usuario != null) {
                TypedQuery<Direccion> queryDireccion = this.entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario", Direccion.class);
                queryDireccion.setParameter("idusuario", IdUsuario);
                usuarioDireccion.Direcciones = queryDireccion.getResultList();
                result.object = usuarioDireccion;

            }
            result.correct = true;

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

    @Override
    public Result UsuarioByIdJPA(int IdUsuario) {
        Result result = new Result();
        try {
            Usuario usuario = this.entityManager.find(Usuario.class, IdUsuario);
            result.object = usuario;
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
    public Result UsuarioUpdateJPA(int IdUsuario, Usuario usuario) {
        Result result = new Result();
        try {
            if (usuario.getIdUsuario() == IdUsuario) {
                Usuario usuarioJPA = this.entityManager.find(Usuario.class, IdUsuario);
                if (usuarioJPA != null) {
                    usuarioJPA.setIdUsuario(usuario.getIdUsuario());
                    usuarioJPA.setUserName(usuario.getUserName());
                    usuarioJPA.setNombre(usuario.getNombre());
                    usuarioJPA.setApellidoPaterno(usuario.getApellidoPaterno());
                    usuarioJPA.setApellidoMaterno(usuario.getApellidoMaterno());
                    usuarioJPA.setEmail(usuario.getEmail());
                    usuarioJPA.setPassword(usuario.getPassword());
                    usuarioJPA.setFechaNacimiento(usuario.getFechaNacimiento());
                    usuarioJPA.setSexo(usuario.getSexo());
                    usuarioJPA.setTelefono(usuario.getTelefono());
                    usuarioJPA.setCelular(usuario.getCelular());
                    usuarioJPA.setCURP(usuario.getCURP());
                    usuarioJPA.setImagen(usuario.getImagen());
                    usuarioJPA.setEstatus(usuario.getEstatus());
                    usuarioJPA.Rol = new com.digis01.MMarinProgrmacionNCapasSpring.JPA.Rol();
                    usuarioJPA.Rol.setIdRol(usuario.Rol.getIdRol());
                    this.entityManager.merge(usuarioJPA);
                    result.correct = true;
                } else {
                    result.correct = false;
                    result.errorMessage = "Usuario no existe";
                }
            }else{
                result.correct = false;
                result.errorMessage = "Error en el IdUsuario";
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
    public Result UsuarioDireccionDeleteJPA(int IdUsuario) {
        Result result = new Result();
        try {
            Usuario usuario = this.entityManager.find(Usuario.class, IdUsuario);
            if (usuario != null) {
                TypedQuery<Direccion> queryDireccion = this.entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario", Direccion.class);
                queryDireccion.setParameter("idusuario", IdUsuario);
                List<Direccion> direcciones = queryDireccion.getResultList();
                for (Direccion direccion : direcciones) {
                    this.entityManager.remove(direccion);
                }

                this.entityManager.remove(usuario);
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

    @Transactional
    @Override
    public Result UsuarioUpdateByEstatusJPA(int IdUsuario, int Estatus) {
        Result result = new Result();
        try {
            Usuario usuarioJPA = this.entityManager.find(Usuario.class, IdUsuario);
            if (usuarioJPA != null) {
                usuarioJPA.setEstatus(Estatus);
                this.entityManager.merge(usuarioJPA);
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
    public Result UsuarioGetAllDinamicoJPA(Usuario usuario) {
        Result result = new Result();
        try {
            String queryBase = "FROM Usuario U";
            String queryNombre = " WHERE LOWER(U.Nombre) LIKE '%'||:nombre||'%'";
            String queryApellidoPaterno = " AND LOWER(U.ApellidoPaterno) LIKE '%'|| :apellidopaterno||'%' ";
            String queryApellidoMaterno = " AND LOWER(U.ApellidoMaterno) LIKE  '%'||:apellidomaterno||'%'";
            TypedQuery<Usuario> queryUsuarios;
            if (usuario.Rol.getIdRol() == 0 && usuario.getEstatus() == -1) {
                queryUsuarios = this.entityManager.createQuery(queryBase + queryNombre + queryApellidoPaterno + queryApellidoMaterno + " ORDER BY U.IdUsuario ", Usuario.class);
                queryUsuarios.setParameter("nombre", usuario.getNombre().toLowerCase());
                queryUsuarios.setParameter("apellidopaterno", usuario.getApellidoPaterno().toLowerCase());
                queryUsuarios.setParameter("apellidomaterno", usuario.getApellidoMaterno().toLowerCase());

            } else {
                if (usuario.Rol.getIdRol() > 0 && usuario.getEstatus() == -1) {
                    String queryRol = " AND U.rol.IdRol = :idrol ";
                    String queryCompleto = queryBase + queryNombre + queryApellidoPaterno + queryApellidoMaterno + queryRol + "ORDER BY U.IdUsuario";

                    queryUsuarios = this.entityManager.createQuery(queryCompleto, Usuario.class);
                    queryUsuarios.setParameter("nombre", usuario.getNombre().toLowerCase());
                    queryUsuarios.setParameter("apellidopaterno", usuario.getApellidoPaterno().toLowerCase());
                    queryUsuarios.setParameter("apellidomaterno", usuario.getApellidoMaterno().toLowerCase());
                    queryUsuarios.setParameter("idrol", usuario.Rol.getIdRol());
                } else if (usuario.Rol.getIdRol() == 0) {
                    String queryEstatus = " AND U.Estatus = :estatus ";
                    String queryCompleto = queryBase + queryNombre + queryApellidoPaterno + queryApellidoMaterno + queryEstatus + "ORDER BY U.IdUsuario";
                    queryUsuarios = this.entityManager.createQuery(queryCompleto, Usuario.class);
                    queryUsuarios.setParameter("nombre", usuario.getNombre().toLowerCase());
                    queryUsuarios.setParameter("apellidopaterno", usuario.getApellidoPaterno().toLowerCase());
                    queryUsuarios.setParameter("apellidomaterno", usuario.getApellidoMaterno().toLowerCase());
                    queryUsuarios.setParameter("estatus", usuario.getEstatus());

                } else {
                    String queryRol = " AND U.rol.IdRol = :idrol";
                    String queryEstatus = " AND U.Estatus =  :estatus ";
                    String queryCompleto = queryBase + queryNombre + queryApellidoPaterno + queryApellidoMaterno + queryRol + queryEstatus + "ORDER BY U.IdUsuario";
                    queryUsuarios = this.entityManager.createQuery(queryCompleto, Usuario.class);
                    queryUsuarios.setParameter("nombre", usuario.getNombre().toLowerCase());
                    queryUsuarios.setParameter("apellidopaterno", usuario.getApellidoPaterno().toLowerCase());
                    queryUsuarios.setParameter("apellidomaterno", usuario.getApellidoMaterno().toLowerCase());
                    queryUsuarios.setParameter("estatus", usuario.getEstatus());
                    queryUsuarios.setParameter("idrol", usuario.Rol.getIdRol());

                }

            }
            List<com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario> usuariosJPA = queryUsuarios.getResultList();
            result.objects = new ArrayList();
            for (com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario usuarioJPA : usuariosJPA) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Usuario = usuarioJPA;
                TypedQuery<Direccion> queryDireccion = this.entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario", Direccion.class);
                queryDireccion.setParameter("idusuario", usuarioJPA.getIdUsuario());
                List<Direccion> direccionesJPA = queryDireccion.getResultList();
                usuarioDireccion.Direcciones = direccionesJPA;
                result.objects.add(usuarioDireccion);

            }
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
