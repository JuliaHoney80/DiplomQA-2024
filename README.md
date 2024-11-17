**Дипломный проект по профессии «Тестировщик»**

**Дипломный проект — автоматизация тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.**
**[Задание по дипломному проекту](https://github.com/netology-code/qa-diploma/blob/master/README.md)**

**Документация:**
1. [План](https://github.com/JuliaHoney80/DiplomQA-2024/blob/main/docs/Plan.md)
2. [Отчёт о проведённом тестировании](https://github.com/JuliaHoney80/DiplomQA-2024/blob/main/docs/Report.md)
3. [Отчёт о проведённой автоматизации](https://github.com/JuliaHoney80/DiplomQA-2024/blob/main/docs/Summary.md)

**Запуск автотестов (подготовка к автотестированию):**
Для работы с проектом необходимо установить следующиe инструменты:
* IntelliJ IDEA 
* Selenide
* Docker, Docker Desktop 
* Dev Tools
* Allure Report
* Браузер - Google Chrome

**Подключение SUT к БД  MySQL, PostgreSQL и Node.js:**
1. Для запуска контейнеров MySQL, PostgreSQL и Node.js в терминале, Local 1, IntelliJ IDEA ввести команду
docker-compose up.
2. Для запуска приложения "Путешествия дня" в терминалe, local 2, вводим команду java-jar .\artifacts\aqa-shop.jar 
либо запустить приложение с помощью правой кнопкой мыши по файлу aqa-shop.jar, далее нажать Run 'aqa-shop.jar'. 
Приложение успешно запущено и можно убедиться пройдя по [ссылке](http://localhost:8080/?ref=https://githubhelp.com).
3. Для запуска автотестов в терминале,local 3, вводим команду ./gradlew test либо запустить автотесты с помощью правой
кнопкой мыши по папке test и нажать Run 'Tests in test'.

**Отчеты**
Для просмотра результаовы выполнения автотестов, в терминале IntelliJ IDEA, local 4, необходимо ввести команду: .\gradlew allureServe 
и в браузере Google Chrome [по адресу](http://192.168.1.211:36559/index.html) откроется Allure Report.

**Завершение работы SUT:**
1. Ввести в терминале, local 5, команду Ctrl+C+y+enter. Данная команда остановит выполнения отчета Allure Report.
2. Ввести в терминале, local 6, команду Ctrl+C. Данная команда остановит работу приложения "Путешествия дня".
3. Ввести в терминале, local 7, команду docker-compose down. Данная команда остановит работу всех контейнеров.
## Шаги:
1. Поднять окружение командой `docker compose up -d`
2. Запустить тесты `.\gradlew test`
3. Выполнить команду для генерации отчета `.\gradlew allureReport --clean`
4. Перейти в директорию `build/reports/allure-report/allureReport` и открыть в браузере файл `index.html`