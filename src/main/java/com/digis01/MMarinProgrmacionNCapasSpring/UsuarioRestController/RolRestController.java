
package com.digis01.MMarinProgrmacionNCapasSpring.UsuarioRestController;

import com.digis01.MMarinProgrmacionNCapasSpring.DAO.RolDAOImplementation;
import com.digis01.MMarinProgrmacionNCapasSpring.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rolapi")
public class RolRestController {
    
    @Autowired
    private RolDAOImplementation rolDAOImplentation;
    
    @GetMapping
      public ResponseEntity RolGetAll(){
         Result result = this.rolDAOImplentation.GetAll();
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
}
