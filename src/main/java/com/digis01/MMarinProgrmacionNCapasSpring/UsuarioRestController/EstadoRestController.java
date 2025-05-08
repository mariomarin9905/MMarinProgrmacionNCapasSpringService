
package com.digis01.MMarinProgrmacionNCapasSpring.UsuarioRestController;

import com.digis01.MMarinProgrmacionNCapasSpring.DAO.EstadoDAOImplementation;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estadoapi")
public class EstadoRestController {
    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;
    
    @GetMapping("/ByIdPais/{IdPais}")
    public ResponseEntity ByIdPais(@PathVariable int IdPais){
        Result result = this.estadoDAOImplementation.ByIdPais(IdPais);
        if (result.correct) {
            if (result.object == null) {
                    return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(result);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
}
