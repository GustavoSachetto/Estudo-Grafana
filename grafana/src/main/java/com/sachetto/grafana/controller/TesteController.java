package com.sachetto.grafana.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/teste")
public class TesteController {

	@GetMapping("/usuario")
	public String usuario() {
		log.info("Bateu no endpoint de usuário");
		return "Endpoint feito para retornar usuário";
	}
	
	@GetMapping("/cliente")
	public String cliente() {
		log.info("Bateu no endpoint de cliente");
		log.warn("Cliente não encontrado");
		return "Endpoint feito para retornar cliente";
	}
}
