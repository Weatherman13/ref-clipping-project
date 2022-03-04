# REF CLIPPING PROJECT
## Краткое описание
Горизонтально масштабируемое приложение по формированию коротких ссылок на Java, состоящее из REST сервисов.

## Стек технологий:
- Java 13
- Spring(Boot,Jpa,Web,Cloud)
- Lombok
- Apache kafka
- БД MySql
- Bucket4j

## Сборка проекта:
```sh
$ cd Eureka
$ mvn package
$ cd ..
$ cd API
$ mvn package
$ cd ..
$ cd Proxy
$ mvn package
$ cd ..
$ cd RateLimiter
$ mvn package
$ cd ..
$ cd RefClippingService
$ mvn package
```
Также необходимо запустить Kafka Server и Zookeeper:
- скачиваем версию кафки 2.12-2.6.0 с официального сайта https://kafka.apache.org/downloads
- запускаем сначала kafka, а потом zookeeper как показано ниже

```sh
$ cd C:\*\kafka_2.12-2.6.0 .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

$ cd C:\*\kafka_2.12-2.6.0 .\bin\windows\kafka-server-start.bat .\config\server.properties
```
И последнее - необходимо поменять конфигурацию подключения на вашу БД. Для этого в сервисах ```RefClippingService``` и ```API``` в файле application.yaml поменяйте данные как в примере ниже на ваши.
```sh
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/*your_of_bd_name*?useSSL=false&serverTimezone=UTC
    username: *username*
    password: *password*
```

> В дальнейшем всё будет перенесено на докер образы
## Схема работы приложения

![Diagram3 drawio](https://user-images.githubusercontent.com/82045585/156694526-e7f20e0e-b44e-42b8-83a6-f5703008bf02.png)


# Краткое описание сервисов
# PROXY SERVICE
## Описание
Данный сервис реализует технологию API Gateway. То есть он принимает, обрабатывает и распределяет запросы,
осуществляет мониторинг и контроль доступа. Route(ы) по которым он распределяет запросы прописаны в  ```application.yaml```.
> Запускается в одном экземпляре на всё приложение.
## Краткое описание классов
В корневом пакете сервиса ```ru.refcreator.proxy``` находится класс ```ProxyApplication```, содержащий в себе точку входа в приложение.
Также он аннотирован ```@EnableEurekaClient```. Что означает, что Eureka service отслеживает состояние запущенного экземпляра сервиса.
## Routes
Пока путь перенаправления всего один - все адреса начинающиеся с ```/api``` перенаправляются на экземпляр API сервиса.
# EUREKA SERVICE
## Описание
Сервис реализующий Server Side Discovery паттерн. Хранит в себе адреса всех сервисов в приложение и позволяет отслеживать
их состояние в реальном времени по адресу ```http://localhost:8761/```.
> Запускается в одном экземпляре на всё приложение.
## Краткое описание классов
В корневом пакете сервиса ```ru.refcreator.eureka``` находится класс ```EurekaApplication```, содержащий в себе точку входа в приложение.
Также он аннотирован ```@EnableEurekaServer```. Что означает, что сервис является Eureka сервером.
> Запускается в одном экземпляре на всё приложение.
# API SERVICE

## Описание
REST-сервис, у которого есть несколько задач:
- Валидировать значение ссылок в клиентских запросах перед их попаданием в основной сервис по генерации коротких ссылок.
- Проверять TTL коротких ссылок, чьё время жизни ограничено 10 минутами.
- Балансировать нагрузку на основной сервис по генерации ссылок.

## Краткое описание классов
В корневом пакете проекта ```ru.thirteenth.api``` находится класс ```ApiApplication```, содержащий в себе точку входа в приложение.
Аннотирован ```@EnableEurekaClient```.
> Можно запускать в любом количестве экземпляров(кол-во экземпляров не может превышать кол-во партиций в топике)

В пакете ```config``` содержатся следующие классы:
- ```ApiConfig``` - класс конфигуации сервиса, содержащий в себе настройки для *топика кафки* и балансировки нагрузки RestTemplate.
> Количество партициий(стандартно 10) в топике зависет от количества консьюмеров (экземпляров ```RefClippinService```);
- ```KafkaProducerConfig``` - класс конфигурации *продюсера кафки*. Содержит все необходимые фабрики для нормальной отправки в *лисенер*;

В пакете ```controller``` содержится следующий класс:
- ```ApiController``` - класс контроллер, перенаправляющий запросы в ```RefClippinService```;

В пакете ```dao``` содержится следующий интерфейс:
- ```ClippingRefRepository```- интерфейс хранилища данных для сущности ClippingRef, логика которого имплементируется с помощью библиотеки Spring Data JPA;

В пакете ```entity``` содержатся следующий классы:
- ```DefaultRef``` - Entity класс, описывающий стандартную ссылку;
- ```ClippingRef``` - Entity класс, описывающий укороченную ссылку;
> Классы ```DefaultRef``` и ```ClippingRef``` в реляционном представлении относятся друг к другу как OneToOne>

В этом же пакете находится пакет ```dao``` содержащий следующие классы:
- ```DefaultUrl``` - класс обёртка для адреса стандартной ссылки, который также имеет поле с уникальным токеном. Используется для передачи url стандартной ссылки в ```RefClippinService```;
- ```StringResponse``` - класс обёртка для ответа на запрос. Тип String;
- ```ExceptionDetails``` - класс предоставляющий клиенту детали об ошибке;

В пакете ```exception``` содержатся классы связанные с обработкой исключений:
- ```GlobalExceptionHandler``` - класс глобальной обработки исключений.

В этом же пакете находится пакет ```dao``` содержащий классы исключений унаследованных от класса ```RunTimeException```:
- ```BlankException``` - выбрасывается если url в клиентском запросе равен null или имеет отступы или пробелы;
- ```ClippingRefNotFoundException``` - Выбрасывается, когда короткая ссылка, которую передал клиент в запросе, не найдена в БД.
- ```DefaultRefNotFoundException``` - Выбрасывается, когда стандартная ссылка, которую передал клиент в запросе, не найдена в БД.
- ```ExpiredLinkException``` - выбрасывается, когда короткая ссылка, переданная в клиентском запросе, устарела. 
- ```RequestTimeoutExceededException``` - выбрасывается, когда запрос так и не был исполнен. Чаще всего связано с большой загрузкой сервера.

В пакете ```service``` содержатся интерфейсы низкоуровневых сервисов, пакет с классами имплиментирующими эти интерфейсы и высокоуровневый сервис;
- ```ClientService``` - класс, являющийся высокоуровневым сервисом, который содержит основной функционал ```API SERVICE```.
- ```KafkaProducer``` - интерфейс, описывающий спецификацию продюсера, связанную с добавлением объекта в топик Кафки;
- ```ClippingRefService``` - интерфейс, описывающий спецификацию низкоуровневого сервиса, который отвечает за работу с репозиторием класса ```ClippingRef```.
- ```ExpirationDateCheckerService``` - интерфейс, описывающий спецификацию низкоуровневого сервиса, который проверяет TTL короткой ссылки.

В этом же пакете находится пакет ```impl``` содержащий низкоуровневые сервисы:
- ```ClippingRefServiceImpl```- класс, который содержит функционал для взаимодействия с реляционным представлением ```ClippingRef```;
- ```ExpirationDateCheckerServiceCheckerServiceImpl```- класс, который содержит функционал по проверке TTL короткой ссылки;
- ```ValidationService```- класс, который содержит функционал для валидирования стандартных и коротких ссылок в запросе клиента;

## Описание внешнего программного интерфейса
### Генерация коротких ссылок
Для создания новой короткой ссылки необходимо послать POST-запрос на адрес ```http://localhost:8080/api/service/create```. В теле запроса дожен находиться объект в формате JSON со строковым параметром ```url```. Этот параметр дожен удовлетворять следующим требованиям:
- не пустая строка;
- не null
- без пробелов и отступов
- строка должна соответствовать формату URL с обязательным указанием протокола
 
В ответе возвращается объект с единственным полем ```response```, в котором указывается сгенерированная короткая ссылка.

Пример запроса:
```http
POST /api/service/create
Host: localhost:8080
Content-Type: application/json
{
	"url": "https://www.google.ru/"
}
```
Пример ответа:
```http
HTTP/1.1 200 OK
Content-Type: application/json
{
    "response": "http://localhost:8080/api/go-to/WHVAeeox"
}
```
В случаях если поле ```url``` будет иметь неверный формат, клиенту возвращается код ```400 Bad Request``` и сообщение с уточнением, а короткая ссылка не будет создана.

Пример запроса:
```http
POST /api/service/create
Host: localhost:8080
Content-Type: application/json
{
	"url": " "
}
```

Пример ответа:
```http
HTTP/1.1 400 Bad Request
Content-Type: application/json
{
    "timestamp": "2022-03-04T04:31:23.6436327",
    "message": "The 'url' field must not be empty or null",
    "status": 400
}
```

### Возврат полной ссылки на основе короткой

Для возврата стандартной ссылки на основе короткой необходимо послать POST-запрос на адрес ```http://localhost:8080/api/get-clip-ref```. В теле запроса дожен находиться объект в формате JSON со строковым параметром ```url```. Этот параметр дожен удовлетворять следующим требованиям:
- не пустая строка;
- не null
- без пробелов и отступов
- строка должна соответствовать формату URL с обязательным указанием протокола
- короткая ссылка должна существовать в БД и не быть просроченной
 
В ответе возвращается объект с единственным полем ```response```, в котором указывается стандартная ссылка.

Пример запроса:
```http
POST /api/get-clip-ref
Host: localhost:8080
Content-Type: application/json
{
    "url": "http://localhost:8080/api/go-to/GYkm9VhB"
}
```
Пример ответа:
```http
HTTP/1.1 200 OK
Content-Type: application/json
{
    "response": "https://www.google.ru/"
}
```
В случаях если поле ```url``` будет иметь неверный формат, клиенту возвращается код ```400 Bad Request``` и сообщение с уточнением.
 
Пример запроса:
```http
POST /api/get-clip-ref
Host: localhost:8080
Content-Type: application/json
{
	"url": " "
}
```

Пример ответа:
```http
HTTP/1.1 400 Bad Request
Content-Type: application/json
{
    "timestamp": "2022-03-04T04:31:23.6436327",
    "message": "The 'url' field must not be empty or null",
    "status": 400
}
```

В случаях если короткая ссылка просрочена, клиенту возвращается код ```410 Gone``` и сообщение с уточнением.
 
Пример запроса:
```http
POST /api/get-clip-ref
Host: localhost:8080
Content-Type: application/json
{
	"url": "http://localhost:8080/api/go-to/Qy8a43Hm"
}
```

Пример ответа:
```http
HTTP/1.1 410 Gone
Content-Type: application/json
{
    "timestamp": "2022-03-04T04:51:38.1767919",
    "message": "Short link expired",
    "status": 410
}
```

В случаях если такой короткой ссылки не существует, клиенту возвращается код ```404 Not Found``` и сообщение с уточнением.
 
Пример запроса:
```http
POST /api/get-clip-ref
Host: localhost:8080
Content-Type: application/json
{
	"url": "http://localhost:8080/api/go-to/Qy8a43HmsQ"
}
```

Пример ответа:
```http
HTTP/1.1 404 Not Found
Content-Type: application/json
{
    "timestamp": "2022-03-04T04:57:02.7270615",
    "message": "Such a short link was not found",
    "status": 404
}
```

### Переход по короткой ссылке
Для перехода по короткой ссылке необходимо осуществить GET-запрос по ссылке,
возвращенной на шаге генерации.
```http://localhost:8080/api/go-to/WHVAeeox```
В случаях если короткое имя не существует в базе данных, клиенту возвращается код ```404 Not Found``` и сообщение с уточнением.

# RATE LIMITER SERVICE

## Описание
REST-сервис, который предоставляет функционал по ограничению трафика при помощи библиотеки Bucket4j. 
Весь сервис состоит из 1 контроллера, в котором существует абстрактная корзина. При каждом запросе к контроллеру в корзину кладётся 1 токен.
Вместимость корзины 100 токенов. Каждые 30 секунд корзина обнуляется и её вместимость снова становится 100. Таким образом сервис не может обработать
больше 100 запросов в 30 секунд(при желании временной лимит можно установить самостоятельно).

## Краткое описание классов
В корневом пакете проекта ```ru.thirteenth.ratelimiter``` находится класс ```RateLimiterApplication```, содержащий в себе точку входа в приложение.
Аннотирован ```@EnableEurekaClient```.
> Можно запускать только в единственном экземпляре.

В пакете ```config``` содержится класс системной конфигурации:
- ```Config``` - класс системной конфигурации.

В пакете ```controller``` содержится класс контроллера с корзиной:
- ```RateLimiterController``` - класс контроллер, через который происходит ограничение трафика.

# REF CLIPPING SERVICE

## Описание
REST сервиc выполняющий всю основную работу: 
- генерация короткой ссылки 
- получение короткой ссылки
- получение стандартной ссылки по короткой

## Алгоритм генерации новой ссылки
Генерация новой ссылки происходит с помощью ```RandomStringUtils.class``` который принимает на вход количество символов, наличие букв и наличие цифр, а после генерирует рандомну символьную строку заданной величины.

## Краткое описание классов
В корневом пакете проекта ```ru.thirteenth.ref_clipping_service``` находится класс ```RefClippingServiceApplication```, содержащий в себе точку входа в приложение.
Аннотирован ```@EnableEurekaClient```.
> Можно запускать в любом количестве экземпляров

В пакете ```config``` содержатся следующие классы:
- ```RefClippingServiceConfig``` - класс конфигуации сервиса;
- ```KafkaConsumerConfig``` - класс конфигурации *консьюмера кафки*;

В пакете ```controller``` содержится следующий класс:
- ```MainController``` - класс контроллер, который осуществляет все основные фукнции сервиса(генерация и получение короткой ссылки, получение короткой ссылки на осное стандартной);

В пакете ```dao``` содержит след. интерфейсы :
- ```ClippingRefRepository```- интерфейс хранилища данных для сущности ClippingRef, логика которого имплементируется с помощью библиотеки Spring Data JPA;
- ```DefaultRefRepository```- интерфейс хранилища данных для сущности DefaultRef, логика которого имплементируется с помощью библиотеки Spring Data JPA;

В пакете ```entity``` содержатся следующий классы:
- ```DefaultRef``` - Entity класс, описывающий стандартную ссылку;
- ```ClippingRef``` - Entity класс, описывающий укороченную ссылку;
> Классы ```DefaultRef``` и ```ClippingRef``` в реляционном представлении относятся друг к другу как OneToOne>

В этом же пакете находится пакет ```dao``` содержащий следующие классы:
- ```DefaultUrl``` - класс обёртка для адреса стандартной ссылки, который также имеет поле с уникальным токеном. Используется для передачи url стандартной ссылки в ```RefClippinService```;
- ```ExceptionDetails``` - класс предоставляющий клиенту детали об ошибке;

В пакете ```exception``` содержатся классы связанные с обработкой исключений:
- ```GlobalExceptionHandler``` - класс глобальной обработки исключений.

В этом же пакете находится пакет ```dao``` содержащий классы исключений унаследованных от класса ```RunTimeException```:
- ```ClippingRefNotFoundException``` - Выбрасывается, когда короткая ссылка, которую передал клиент в запросе, не найдена в БД.
- ```DefaultRefNotFoundException``` - Выбрасывается, когда стандартная ссылка, которую передал клиент в запросе, не найдена в БД.
- ```RequestTimeoutExceededException``` - выбрасывается, когда запрос так и не был исполнен. Чаще всего связано с большой загрузкой сервера.

В пакете ```service``` содержатся интерфейсы низкоуровневых сервисов, пакет с классами имплиментирующими эти интерфейсы, высокоуровневый сервис, *сервис консьюмера Кафки*;
- ```ClientService``` - класс, являющийся высокоуровневым сервисом, который содержит основной функционал ```CLIPPING REF SERVICE```.
- ```ClippingRefService``` - интерфейс, описывающий спецификацию низкоуровневого сервиса, который отвечает за работу с репозиторием класса ```ClippingRef```.
- ```DefaultRefService``` - интерфейс, описывающий спецификацию низкоуровневого сервиса, который отвечает за работу с репозиторием класса ```DefaultRef```.
- ```GeneratorService``` - интерфейс, описывающий спецификацию низкоуровневого сервиса, который генерирует новую ссылку.
- ```GetClippingRefService``` - интерфейс, описывающий спецификацию низкоуровневого сервиса, который получает короткую ссылку на основе стандартной.
- ```GetDefRefByClipRef``` - интерфейс, описывающий спецификацию низкоуровневого сервиса, который получает стандартную ссылку на основе короткой.

В этом же пакете находится пакет ```impl``` содержащий низкоуровневые сервисы:
- ```ClippingRefServiceImpl```- класс, который содержит функционал для взаимодействия с реляционным представлением ```ClippingRef```;
- ```DefaultRefServiceImpl```- класс, который содержит функционал для взаимодействия с реляционным представлением ```DefaultRef```;
- ```GeneratorServiceImpl```- класс, который содержит функционал для создания новой короткой ссылки;

Также в пакете ```service``` есть пакет ```consumer``` который содержит класс с лисенером.
- ```ConsumerService```- класс, который является высокоуровневым сервисом и содержит функционал *KafkaListener*: вынимает запрос клиента из топика кафки, обращается к ```RATE LIMITER SERVICE``` 
- для того, чтобы проверить наличие доступных запросов, после чего возвращается обратно и генерирует короткую ссылку;

