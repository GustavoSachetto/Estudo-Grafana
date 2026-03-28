# Estudo-Grafana

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/525ebeb0-8423-4dbb-95f4-40bf3eecec9c" />

Este projeto demonstra o rastreamento de requisições entre microsserviços Spring Boot utilizando o cabeçalho X-Request-ID para correlacionar logs vindos do frontend.

## Funcionamento do Rastreamento

1.  **Captura**: O `RequestIdFilter` lê o header `X-Request-ID` da requisição e o armazena no **MDC** (contexto da thread).
2.  **Log**: O Logback exporta o log em formato JSON para o Loki, incluindo automaticamente o campo `x_request_id`.
3.  **Propagação**: Um interceptor no cliente HTTP (Feign ou RestTemplate) recupera o ID do MDC e o injeta no header da próxima chamada.

## Exemplo de Código

```java
@Slf4j
@RestController
@RequestMapping("/teste")
@RequiredArgsConstructor
public class TesteController {

    private final RestTemplate restTemplate;

    @GetMapping("/vaga")
    public String vaga() {
        log.info("Iniciando chamada externa");
        String resposta = restTemplate.getForObject("http://localhost:8080/teste/usuario", String.class);
        log.info("Resposta recebida: {}", resposta);
        return "Sucesso: " + resposta;
    }
}
```

## Infraestrutura (Docker Compose)

```yaml
version: '3.8'

services:
  loki:
    image: grafana/loki:latest
    container_name: loki
    ports:
      - "3100:3100"

  prometheus:
    image: prom/prometheus:latest
    container_name: prometheus
    ports:
      - "9090:9090"

  grafana:
    image: grafana/grafana-oss:latest
    container_name: grafana
    ports:
      - "3000:3000"
    environment:
      - GF_SECURITY_ADMIN_PASSWORD=admin
```

## Consulta no Grafana (Loki)

Para visualizar o rastro completo de uma requisição em todos os serviços:

```log
{app=~"grafana"} | json | x_request_id = "teste-123"
```

**************************************
