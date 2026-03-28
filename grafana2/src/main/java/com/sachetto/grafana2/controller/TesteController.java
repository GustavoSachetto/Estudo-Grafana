package com.sachetto.grafana2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/teste")
@RequiredArgsConstructor
public class TesteController {

	private final RestTemplate restTemplate;

    @GetMapping("/vaga")
    public String vaga() {
        log.info("Bateu no endpoint de vaga - Iniciando chamada externa");
        String resposta = restTemplate.getForObject("http://localhost:8080/teste/usuario", String.class);
        log.info("Resposta recebida do serviço de usuário: {}", resposta);
        return "Vaga processada com: " + resposta;
    }
}
