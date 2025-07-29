# Finance tracker для контроля расходов

**Простой CLI трекер для личных расходов с поддержкой Json, CSV и PostgreSql(В будущем)**

---

## Finance tracker умеет:
- Выполнять стандартные CRUD операции: Добавление, удаление, обновление;
- Выводить сумму расходов за конкретный месяц или в общем
- Эксортировать и импортировать данные из json формата и хранить созданые задачи в нем
- Экспортировать в CSV формат
- В будущем интеграция с PostgreSql с помощью jdbc

---

## Требования для запуска:
- Java version 17+
- Maven 3.6+ (или Gradle)
- (для PostgreSQL‑режима) PostgreSQL 14+
- Опционально: pgAdmin или любой SQL‑клиент

---

## Установка и запуск:
1) Клонируем репозиторий:
 ```bash
git clone https://github.com/…/FinanceTracker.git
cd FinanceTracker
 ```
2) Строим проект maven
```bash
mvn clean package
```
3) Редактируем конфиг src/main/resources/application.properties:
```bash
# Тип хранилища: json, csv, postgres
storage.type=json

# Для JSON‑стоража
json.path=./data/expenses.json

# Для CSV‑импорта/экспорта
csv.path=./data/expenses.csv

# Для PostgreSQL
jdbc.url=jdbc:postgresql://localhost:5432/financetracker
jdbc.user=postgres
jdbc.password=secret
```

4) Запустить CLi:
```bash
java -jar target/financetracker.jar

```
---

## Команды CLI

/add или add — добавляет новую трату в трекер

/delete или delete — удаляет существующую трату по её ID

/all или all — показывает все имеющиеся траты

/update или update — позволяет изменить одно или все поле у выбранной траты

/sum или sum — выводит общую сумму всех трат

/month или month — показывает сумму трат за выбранный месяц

/csv или csv — экспортирует все траты в CSV‑файл для открытия в Excel или другом редакторе

/back — возвращает в главное меню

/exit или exit — выход из программы


---

## Структура проекта

src/
 ├─ main/
 │   ├─ java/com/nikitanevmyvaka/expensetracker/
 │   │   ├─ model/        # Expense, Category
 │   │   ├─ repository/   # интерфейсы и реализации хранения
 │   │   ├─ service/      # бизнес‑логика
 │   │   ├─ cli/          # команды и ввод‑вывод
 │   │   └─ config/       # загрузка application.properties
 │   └─ resources/
 │       └─ application.properties
 └─ test/                # юнит‑ и интеграционные тесты

