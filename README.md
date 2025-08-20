# API de Reserva de Salas  

Projeto desenvolvido na disciplina **Programação Web II** para gerenciar a reserva de salas. 
Projeto desenvolvido por Caren Beatriz e Daiane Maria.

---

## 🚀 Como rodar o projeto  

### 🔧 Pré-requisitos
- **Java 21**
- **Maven 3.9+**
- **MySQL 8.0+**

### ▶️ Executar a aplicação
1. Clone o repositório:
   ```bash
   git clone https://github.com/daianesr35/reserva-salas-api.git
   
2. Entre no diretório do projeto:
bash
cd reserva-salas-api

3.Configure o banco de dados no arquivo src/main/resources/application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/reservas?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

4.Rode a aplicação:

bash
mvn spring-boot:run

🔑 Variáveis de ambiente
JWT_SECRET: chave secreta para geração de tokens
JWT_EXPIRATION: tempo de expiração do token em ms

Essas variáveis podem ser configuradas no application.properties ou em variáveis do sistema.

📌 Endpoints principais
Alguns endpoints disponíveis:

POST /auth/register → Registrar usuário

POST /auth/login → Autenticar e gerar token JWT

GET /salas → Listar salas

POST /salas → Criar nova sala

POST /reservas → Criar reserva

PUT /reservas/{id} → Atualizar reserva

DELETE /reservas/{id} → Cancelar reserva

🧪 Testes com Postman
Na pasta postman/ você encontra:

ReservaSalas.postman_collection.json → coleção de requisições

ReservaSalas-Local.postman_environment.json → environment local

Como importar:
Abra o Postman.

Vá em Import (canto superior esquerdo).

Selecione os arquivos da pasta postman/.

Ajuste o valor de base_url no environment (ex.: http://localhost:8080).

Faça login (POST /auth/login) e cole o token JWT nas próximas requisições.

👩‍💻 Autoria
Projeto desenvolvido por Daiane Maria dos Santos Ribeiro e Caren Beatriz Silva Oliveira
Disciplina: Programação Web II
Professor: Francisco Junio
Instituto Federal do Sertão Pernambucano (IFSertãoPE) campus Salgueiro

---

