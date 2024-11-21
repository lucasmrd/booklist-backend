# Etapa 1: Build com Maven
FROM maven:3.9.5-eclipse-temurin-17 AS builder

WORKDIR /app

# Copiar arquivos do projeto
COPY pom.xml ./

# Fazer o download das dependências para offline
RUN mvn dependency:go-offline

# Copiar o código-fonte
COPY src ./src

# Build da aplicação sem rodar testes
RUN mvn clean package -DskipTests

# Etapa 2: Runtime com JRE
FROM eclipse-temurin:17-jre

WORKDIR /app

# Instalar dependências nativas para o Couchbase
RUN apt-get update && apt-get install -y libssl-dev && rm -rf /var/lib/apt/lists/*

# Copiar o JAR gerado na etapa de build
COPY --from=builder /app/target/*.jar app.jar

# Expor a porta da aplicação
EXPOSE 8080

# Configurar opções JVM para resolver o problema do tcnative
ENTRYPOINT ["java", "-Dcom.couchbase.forceDefaultSslContext=true", "-jar", "app.jar"]
