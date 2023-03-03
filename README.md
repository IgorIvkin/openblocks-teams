# OpenBlocks "Команды"

### Инициатива OpenBlocks

Инициатива OpenBlocks &mdash; это проект с открытым исходным кодом, целью которого
является предоставить открытые и масштабируемые решения уровня предприятия.

### Описание

Сервис "Команды" предназначен для управления командами в предприятии. Он позволяет
группировать сотрудников предприятия по подразделениям и командам, а также присваивать
им должности и права.

В сервисе также демонстрируется подход к работе с сервисом **Keycloak** в вашем предприятии
через UI. Сервис имеет веб-часть, которая взаимодействует с Keycloak посредством Javascript-адаптера.

### Основная конфигурация

#### Backend

Обратите внимание на следующие секции в конфигурации сервиса.

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          jwk-set-uri: http://localhost:8534/realms/infra/protocol/openid-connect/certs
          issuer-uri: http://localhost:8534/realms/infra
```

В этом блоке задаются URL вашего сервиса Keycloak, "jwk-set-uri" служит для
получения JWKS-ключей и для последующей валидации JWT-токена, с которым приходят
запросы в сервис "Роли".

#### Frontend

В Keycloak вашего предприятия вам необходимо создать клиента (Clients), у которого
должны быть права использовать стандартный поток аутентификации (Standard Flow).

Создав клиента, выберите в Keycloak опцию **Action** -> **Download adapter config** и выберите
формат JSON. В результате вы скачаете файл keycloak.json, который нужно будет положить в корень
вашего веб-приложения. 

С помощью этого файла будет проинициализирован Javascript-адаптер
для Keycloak.

Файл имеет приблизительно следующую структуру:

```json
{
  "realm": "infra",
  "auth-server-url": "http://localhost:8534/",
  "ssl-required": "external",
  "resource": "teams-web-app",
  "verify-token-audience": true,
  "credentials": {
    "secret": "hMUJTeVqTN3L8jz4cAhLE82l7BgCcv0f"
  },
  "confidential-port": 0,
  "policy-enforcer": {}
}
```




## Полезные ссылки
* [OpenBlocks "Пользователи"](https://github.com/IgorIvkin/openblocks-users)
* [OpenBlocks "Роли"](https://github.com/IgorIvkin/openblocks-roles)
* [Документация по Keycloak](https://www.keycloak.org/documentation)