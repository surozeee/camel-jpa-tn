# Camel JPA importer (MySQL 5 -> Postgres)

Spring Boot + Apache Camel JPA route that pulls rows from a MySQL source table and writes them into a Postgres target table while marking the source row as exported.

## Prerequisites
- Java 25 (or newer toolchain)
- Gradle 8+ (or use the Gradle wrapper if added)
- MySQL 5.x (or compatible) with a `customers` table
- PostgreSQL with a `customers_import` table

## Configure
Update `src/main/resources/application.yml` (or supply environment variables) for your DB hosts, users, and passwords. The important properties are:
- `spring.datasource.mysql.*` (source)
- `spring.datasource.postgres.*` (target)

Both Hibernate contexts default to `none` for DDL; create tables yourself:
```sql
-- MySQL
create table customers (
  id bigint auto_increment primary key,
  name varchar(255) not null,
  email varchar(255) not null unique,
  created_at timestamp not null,
  exported boolean not null default 0
);

-- Postgres
create table customers_import (
  id bigserial primary key,
  source_id bigint not null,
  name varchar(255) not null,
  email varchar(255) not null unique,
  created_at timestamp not null
);
```

## Run
```
gradle bootRun
```

The Camel route `mysql-to-postgres-import` polls MySQL every 5s using the named query `SourceCustomer.fetchUnexported`. Each row is mapped in `CustomerProcessor` and persisted to Postgres; the source row is flagged `exported=true` so it is skipped on the next poll.

## Adjustments
- Tweak poll size and delay via `maximumResults` and `delay` in `ImportRouteBuilder`.
- Replace the simple processor with your own transformations or validations.
- To delete rows after import, change the JPA consumer URI `consumeDelete=true` in the route.
