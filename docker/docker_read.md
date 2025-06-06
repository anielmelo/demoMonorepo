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


## Pipeline (implementação)

pipeline para executar o sonarqube automaticamente ao realizar (pushs e pull_requests):
```sh
on:
  push:
    branches:
      - main
      - dev
  pull_request:
      types: [opened, synchronize, reopened]

name: Main Workflow
jobs:
  sonarqube:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v4

    - name: CloudFlare auth
      uses: Boostport/setup-cloudflare-warp@v1.12.0
      with:
        organization: ideia
        auth_client_id: ${{ secrets.CLOUDFLARE_AUTH_CLIENT_ID }}
        auth_client_secret: ${{ secrets.CLOUDFLARE_AUTH_CLIENT_SECRET }}

    - name: SonarQube Scan
      uses: SonarSource/sonarqube-scan-action@v5.2.0
      env:
        SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
        SONAR_HOST_URL: ${{ vars.SONAR_HOST_URL }}
      with:
        args: >
          -Dsonar.projectKey=harpia
          -Dsonar.sources=.
          -Dsonar.projectBaseDir=apps
        scanMetadataReportFile: apps/.scannerwork/report-task.txt
    
    - name: SonarQube Quality Gate check
      id: sonarqube-quality-gate-check
      uses: sonarsource/sonarqube-quality-gate-action@v1.1.0
      with:
        projectBaseDir: apps
        pollingTimeoutSec: 600
        scanMetadataReportFile: apps/.scannerwork/report-task.txt
      env:
        SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
        SONAR_HOST_URL: ${{ vars.SONAR_HOST_URL }}
```