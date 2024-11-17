## Шаги:
1. Поднять окружение командой `docker compose up -d`
2. Запустить тесты `.\gradlew test`
3. Выполнить команду для генерации отчета `.\gradlew allureReport --clean`
4. Перейти в директорию `build/reports/allure-report/allureReport` и открыть в браузере файл `index.html`