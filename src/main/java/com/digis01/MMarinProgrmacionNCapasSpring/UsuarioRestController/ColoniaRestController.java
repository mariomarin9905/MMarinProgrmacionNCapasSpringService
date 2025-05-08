package com.digis01.MMarinProgrmacionNCapasSpring.UsuarioRestController;

import com.digis01.MMarinProgrmacionNCapasSpring.DAO.ColoniaDAOImplementation;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coloniaapi")
public class ColoniaRestController {
    
    @Autowired
    private ColoniaDAOImplementation coloniaDAOImplementation;
    
    @GetMapping("ByIdMunicipio/{IdMunicipio}")
    public ResponseEntity ColoniaByIdMunicipio(@PathVariable int IdMunicipio) {
        Result result = this.coloniaDAOImplementation.BydIdMunicipio(IdMunicipio);
        if (result.correct) {
            if (result.object == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(result);
            }
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
    @GetMapping("ByCodigoPostal/{CodigoPostal}")
     public ResponseEntity ColoniaByCodigoPostal(@PathVariable String CodigoPostal)    {
        Result result = this.coloniaDAOImplementation.ByCodigoPostal(CodigoPostal);
        if (result.correct) {
            if (result.object == null) {
                return ResponseEntity.notFound().build();
            }else{
                return ResponseEntity.ok(result);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }   
}
