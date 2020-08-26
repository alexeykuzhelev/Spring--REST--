# Реализовать операции REST-сервиса: прием параметров и возврат данных – в JSON на Spring

Spring-приложение, предоставляющее REST-сервис для приема и регистрации операций в базе 
данных по расчетам с водителями. У каждого водителя может быть несколько лицевых счетов.

Для реализации использованы: Gradle, PostgreSql, Java 8, Spring Boot, Spring Data, QueryDSL.
Среда разработки IntelliJ IDEA.

Использованы след.библиотеки зависимостей:
spring-boot-starter-data-rest
spring-boot-starter-data-jpa
postgresql
querydsl-apt
querydsl-jpa
validation-api

1. Подключение к СУБД
Параметры подключения находятся в /src/main/resources/application.properties
По дефолту для PostgreSql:
- username=postgres
- password=aleks
- база данных используется стандартная: postgres

Для администрирования и просмотра базы postgres использовал pgAdmin 4.

В базе хранятся данные о:
- водителях (идентификатор, имя, телефон);
- счетах (идентификатор, идентификатор владельца, имя счета, количество денег);
- операциях (транзакциях) — переводах денег между счетами за период и/или по заданному пользователю.

2. Сборка и запуск

Приложение собирается и деплоится в IntelliJ IDEA:
сначала произведем сборку проекта командой: gradle build (можно через меню IDEA)
затем запустить приложение (через меню Run для 'SpringBootRestServicePaymentsApplication').
Для подключения к серверу PostgreSQL использован графический клиент pgAdmin.

3.  Примеры запросов к REST-сервису

    Rest-сервисы будут доступны по адресу:
http://localhost:8080/api/{сервис}/page/{pageNum}
Где {сервис}:
client - клиент (водитель)
account - счета водителей
operation - операции со счетами

{pageNum} - номер запрашиваемой страницы для постраничного отображения
balance - баланс на счету
amount - сумма, которая снимается с одного счета srcAccount и переводятся на другой счет dstAccount


                                        Примеры GET-запросов
										
Вывод операций (транзакций) за период
http://localhost:8080/api/operation/page/1?from_date=2020-08-11&to_date=2020-08-12
curl http://localhost:8080/api/operation/page/1?from_date=2020-08-11\&to_date=2020-08-12


                                        Примеры POST-запросов

            POST-запросы для заполнения таблиц account и client:
1) Пример через программу Postman: http://localhost:8080/api/account/

    Заполняем данные водителей:
http://localhost:8080/api/client/   - URL POST-запросов
{
    "id": 0,
    "name": "Иванов",
    "phone": "+79181111111"
}

{
    "id": 1,
    "name": "Петров",
    "phone": "+79181111112"
}

    Заполняем данные по счетам водителей:
http://localhost:8080/api/account/   - URL POST-запросов
{
    "id": 0,
    "client": {
        "id": 0
    },
    "name": "Карта зарплатная",
    "balance": "00.00"
}

{
    "id": 1,
    "client": {
        "id": 0
    },
    "name": "Карта кредитная",
    "balance": "00.00"
}

{
    "id": 2,
    "client": {
        "id": 1
    },
    "name": "Карта зарплатная",
    "balance": "00.00"
}

2) Второй вариант заполнения - загрузка данных из файла src/main/resources/import.sql
insert into client(id,name,phone) values(0,'Иванов','+79181111111'),(1,'Петров','+79181111112');
insert into account(id,client_id,balance,name) values (0,0,0.00,'Карта зарплатная'),(1,0,0.00,'Карта кредитная'),(2,1,0.00,'Карта зарплатная');

            POST-запросы для заполнения таблицы operation:
    Сумма 20.00, которая списывается со счета srcAccount с id=1 и переводится на счет dstAccount с с id=2:
1) Пример через программу Postman: http://localhost:8080/api/operation/
{
    "id": 0,
    "ddate": "2020-08-12",
    "srcAccount": {
        "id": 1
    },
    "dstAccount": {
        "id": 2
    },
    "amount": "20.00"
}

    Ответ:
{
    "id": 1,
    "ddate": "2020-08-12",
    "srcAccount": {
        "id": 1,
        "client": {
            "id": 0,
            "name": "Иванов",
            "phone": "+79181111111"
        },
        "name": "Карта кредитная",
        "balance": "-20.00"
    },
    "dstAccount": {
        "id": 2,
        "client": {
            "id": 1,
            "name": "Петров",
            "phone": "+79181111112"
        },
        "name": "Карта зарплатная",
        "balance": "20.00"
    },
    "amount": "20.00"
}	

2) Второй вариант заполнения - загрузка данных из файла src/main/resources/import.sql
insert into operation(id,src_account_id,dst_account_id,amount,ddate) values (0,1,2,20.00,'2020-08-12');



