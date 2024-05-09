# MCTasks
Общая информация: <br/>
Maven version: 3.9.6 <br/>
в папке target хранится jar MyProducts-0.0.1-SNAPSHOT.jar <br/>

Ветка t1: <br/>
Java version: 8 <br/>


Ветка t2: <br/>
Java version: 17.0.10 <br/>
!Если при запуске jar ничего не происходит - нужно запустить cmd от администратора <br/>

Параметры подключения к PostgreSQL (из application.properties):<br/>
username = postgres; password = admin123; <br>

Скрипт для таблицы product:<br/>
create table product(<br/>
 name varchar(255) not null primary key,<br/>
 description varchar(4095),<br/>
 price double precision default 0,<br/>
 instock boolean default false<br/>
);<br/>
