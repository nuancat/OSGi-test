# OSGi-test
Обкатка технологии OSGi, на примере копировальщика файлов на основе конфигураций

Запуск 
1) Переходим консолью в каталог проекта, пишем mvn clean verify (проект собирается)
2) Запускаем apache karaf (./karaf), устанавливаем feature:install jetty
3) В папке с проектом заходим в каталог copier/target и копируем файл copier1.jar в папку deploy у apache karaf
4) Переходим в браузер (либо генератор запросов http типа postman), отправляем запрос
localhost:8000/addTask?source='_путь_исходная_папка_'&destination'_путь_папка _назначения_'&mask='_маска_'
5) Появляется сообщение об успешном либо неуспешном копировании

OSGi project - file copier with http API interface
1) Open the project directory in console
2) Type 'mvn clean verify'
3) Open apache karaf in new terminal window (./karaf) and type there feature:install jetty. Now it's have installed the jetty feature. 
4) Copy the jar bundle from copier/target directory to apache karaf's deploy folder. (Bundle will deploy automatically)
5) Open web-browser and type the URL: localhost:8000/addTask?source='_source_folder_'&destination'_destination_folder'&mask='_mask'
6) You should see the result answer
