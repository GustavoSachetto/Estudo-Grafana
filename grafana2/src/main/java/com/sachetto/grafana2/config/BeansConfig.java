package com.sachetto.grafana2.config;

import org.slf4j.MDC;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeansConfig {
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
	    return builder
	    		.additionalInterceptors((request, body, execution) -> {
	                String requestId = MDC.get("x_request_id");
	                
	                if (requestId != null) {
	                    request.getHeaders().add("X-Request-ID", requestId);
	                }
	                
	                return execution.execute(request, body);
	            })
	    		.build();
	}
}
