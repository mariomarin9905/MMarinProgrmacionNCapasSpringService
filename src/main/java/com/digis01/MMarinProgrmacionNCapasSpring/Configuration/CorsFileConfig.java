
package com.digis01.MMarinProgrmacionNCapasSpring.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration 
public class CorsFileConfig {
    @Bean
    public CorsFilter CorsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsconfig = new CorsConfiguration();
        corsconfig.addAllowedOrigin("*");
         corsconfig.addAllowedMethod("GET");      
        corsconfig.addAllowedMethod("DELETE");
        corsconfig.addAllowedMethod("PATCH");
        corsconfig.addAllowedHeader("*");
        source.registerCorsConfiguration("/**", corsconfig);
        return new CorsFilter(source);
    }
}
