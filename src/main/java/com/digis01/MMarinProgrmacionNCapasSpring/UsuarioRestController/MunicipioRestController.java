
package com.digis01.MMarinProgrmacionNCapasSpring.UsuarioRestController;

import com.digis01.MMarinProgrmacionNCapasSpring.DAO.MunicipioDAOImplementation;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/municipioapi")
public class MunicipioRestController {
    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;
    
    @GetMapping("ByIdEstado/{IdEstado}")
     public ResponseEntity MunicipioByIdEstado(@PathVariable int IdEstado){
        Result result = this.municipioDAOImplementation.ByIdEstado(IdEstado);
        if (result.correct) {
            if (null == result.object) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(result);
            }            
        } else {            
            return ResponseEntity.badRequest().body(result);
        }                
    }
    
}
