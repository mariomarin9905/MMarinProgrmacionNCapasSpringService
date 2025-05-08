package com.digis01.MMarinProgrmacionNCapasSpring.UsuarioRestController;

import com.digis01.MMarinProgrmacionNCapasSpring.DAO.DireccionDAOImplementation;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Direccion;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Result;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/direccionapi")
public class DireccionRestController {

    @Autowired
    private DireccionDAOImplementation direccionDAOImplementation;

    @GetMapping("/GetById/{IdDireccion}")
    public ResponseEntity DireccionGetById(@PathVariable int IdDireccion) {
        Result result = this.direccionDAOImplementation.GetById(IdDireccion);
        if (result.correct) {
            if (result.object != null) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(404).body(null);
            }
        } else {
            return ResponseEntity.status(400).body(result);
        }

    }

    @PostMapping("/Add")
    public ResponseEntity DireccionAdd(@RequestParam int IdUsuario, @RequestBody Direccion direccion) {
        direccion.Usuario = new Usuario();
        direccion.Usuario.setIdUsuario(IdUsuario);
        Result result = this.direccionDAOImplementation.Add(direccion);
        if (result.correct) {
            return ResponseEntity.status(201).body(result);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/Update")
    public ResponseEntity DireccionUpdate(@RequestParam int IdDireccion,@RequestParam int IdUsuario ,@RequestBody Direccion direccion) {
        direccion.Usuario = new Usuario();
        direccion.Usuario.setIdUsuario(IdUsuario);
        Result result = this.direccionDAOImplementation.Update(IdDireccion, direccion);
        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/Delete/{IdDireccion}")
    public ResponseEntity DireccionDelete(@PathVariable int IdDireccion) {
        Result result = this.direccionDAOImplementation.Delete(IdDireccion);
        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

}
