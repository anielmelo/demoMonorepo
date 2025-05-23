# USO DOCKER - SONARQUBE

arquivo de configuração do sonarqube:
```sh
services:
  sonarqube:
    image: sonarqube:lts-community
    depends_on:
      - db
    networks:
      - sonar_net
    environment:
      - SONAR_JDBC_URL=jdbc:postgresql://db:5432/sonar
      - SONAR_JDBC_USERNAME=sonar
      - SONAR_JDBC_PASSWORD=sonar
      - SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true
    ports:
      - "9000:9000"
    volumes:
      - sonarqube_data:/opt/sonarqube/data
      - sonarqube_extensions:/opt/sonarqube/extensions
      - sonarqube_logs:/opt/sonarqube/logs

  db:
    image: postgres:15
    environment:
      POSTGRES_USER: sonar
      POSTGRES_PASSWORD: sonar
      POSTGRES_DB: sonar
    networks:
      - sonar_net
    volumes:
      - sonar_db:/var/lib/postgresql

networks:
  sonar_net:
    driver: bridge
volumes:
  sonarqube_data:
  sonarqube_extensions:
  sonarqube_logs:
  sonar_db:
```

comando para rodar o sonarsource-scanner:
```sh
sudo docker container run --rm --network=host -e SONAR_HOST_URL="http://localhost:9000" -v "./apps:/usr/src" sonarsource/sonar-scanner-cli -Dsonar.projectKey=<nome do projeto>   -Dsonar.sources=.   -Dsonar.host.url=<url do sonarqube>   -Dsonar.token=<token>
```

comandos de remoção de cache:
```sh
sudo rm -rf /tmp/.scannerwork
sudo rm -rf ~/.sonar/cache
```

## Actions
- sonarqube para avaliar o código automaticamente na pipeline (gerar estatísticas):
[Official SonarQube Scan](https://github.com/marketplace/actions/official-sonarqube-scan)

- sonarqube para bloquear novos códigos possivelmente problemáticos:
[SonarQube Quality Gate Check](https://github.com/marketplace/actions/sonarqube-quality-gate-check)

#### PONTOS DE MELHORA
- [ ] automatizar a análise de código na pipeline
- [ ] estudar maneiras de implementar o sonarqube em uma arquitetura fulstack de maneira escalável
- [ ] estudar o arquivo de configuração do sonarqube .properties
