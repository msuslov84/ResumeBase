# Web-приложение "База данных резюме"

## Учебный проект, реализованный в рамках курса BaseJava (Java Online Projects)

### Описание:
В рамках проекта, представляющего собой CRUD приложение, было реализовано несколько вариантов хранения резюме:
- в сортированном и не сортированном массиве
- в коллекциях (List, Map)
- в файловой системе:
  - с использованием File и Path API
  - в стандартной и кастомной сериализации Java
  - в формате JSON (с использованием Google Gson)
  - в формате XML (с использованием JAXB)
-  в реляционной базе (PostgreSQL)

Приложение поддерживает добавление, удаление, редактирование и просмотр резюме с помощью пользовательского UI,
реализованного с помощью JSP страниц с поддержкой CSS. Интерфейс реализован в двух стилях: светлой и темной темах:

![img.png](img/img1.png)

![img.png](img/img2.png)

При реализации проекта были задействованы следующие технологии:
- Java 11, JUnit 5, Logging - для разработки, тестирования и логирования;
- GSON, JAXB - для сериализации при файловом варианте хранения данных;
- SQL, PostgreSQL - для организации хранения данных в БД и работы с ней; 
- Servlet API, - для клиент-серверной работы приложения;
- HTML, JSP, JSTL, CSS - для реализации UI;
- Maven, Tomcat - для сборки и деплоя приложения.