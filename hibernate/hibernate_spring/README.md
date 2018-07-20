* Tables in database:

Create tables with
```
CREATE TABLE `cars` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mark` varchar(30) DEFAULT NULL,
  `model` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
CREATE TABLE `services` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
CREATE TABLE `service` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `service_date` datetime DEFAULT NULL,
  `price` float DEFAULT NULL,
  `car_id` int(11) DEFAULT NULL,
  `service_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`car_id`) REFERENCES `cars` (`id`) ON DELETE CASCADE,
  FOREIGN KEY (`service_id`) REFERENCES `services` (`id`) ON DELETE CASCADE
)
```

Fill tables like:
```
insert into cars(mark, model) values ("BMV", "X5");
insert into cars(mark, model) values ("BMV", "X7");
insert into cars(mark, model) values ("VW", "Bug");
insert into cars(mark, model) values ("VW", "Golf V");

insert into services (name) values ("Auto help");
insert into services (name) values ("Auto repear");
insert into services (name) values ("Best repear");

insert into service (service_date, price, car_id, service_id) values ("2018-06-27", 12.98, 1,1);
insert into service (service_date, price, car_id, service_id) values ("2018-06-28", 42.00, 1,2);
insert into service (service_date, price, car_id, service_id) values ("2018-06-28", 14.50, 3,3);
```
