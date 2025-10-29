# Trabajo Practico Consultas JPQL
Dado el modelo de clases de entidad trabajado en clases y disponible en:

## Realizar las siguientes consultas JPQL
### Ejercicio 1: Listar todos los clientes
Realiza una consulta para listar todos los clientes registrados en el sistema.

### Ejercicio 2: Listar todas las facturas generadas en el último mes
Escribe una consulta para obtener todas las facturas emitidas en el último mes a partir
de la fecha actual.

### Ejercicio 3: Obtener el cliente que ha generado más facturas
Escribe una consulta para obtener el cliente que ha emitido más facturas.
Pista: Deberá usar GROUP BY, COUNT, ORDER BY

### Ejercicio 4: Listar los artículos más vendidos
Crea una consulta que devuelva los productos más vendidos, ordenados por la
cantidad total vendida.
Pista: Deberá usar SUM, GROUP BY Y JOIN para unir FacturaDetalle y Artículo

### Ejercicio 5: Consultar las facturas emitidas en los 3 últimos meses de un cliente específico
Realiza una consulta que devuelva todas las facturas emitidas a un cliente específico
(identificado por su ID), en los últimos 3 meses.

### Ejercicio 6: Calcular el monto total facturado por un cliente
Escribe una consulta que calcule el monto total facturado por un cliente específico.

### Ejercicio 7: Listar los Artículos vendidos en una factura
Crea una consulta para listar todos los Artículos vendidos en una factura específica,
identificada por su ID.

### Ejercicio 8: Obtener el Artículo más caro vendido en una factura
Escribe una consulta que devuelva el Artículo con el mayor precio vendido en una
factura específica.

### Ejercicio 9: Contar la cantidad total de facturas generadas en el sistema
Realiza una consulta que cuente el total de facturas generadas en el sistema.

### Ejercicio 10: Listar las facturas cuyo total es mayor a un valor determinado
Crea una consulta que devuelva las facturas cuyo total es mayor a un valor específico.

### Ejercicio 11: Consultar las facturas que contienen un Artículo específico, filtrando por el nombre del artículo
Escribe una consulta que devuelva las facturas que incluyen un Artículo específico,
identificado por su nombre.

### Ejercicio 12: Listar los Artículos filtrando por código parcial
Crea una consulta para listar todos los Artículos cuyo código coincida total o
parcialmente\
Pista: Deberá usar LIKE

### Ejercicio 13: Listar todos los Artículos cuyo precio sea mayor que el promedio de los precios de todos los Artículos

Crea una consulta que liste todos los Artículos cuyo precio sea mayor que el promedio
de todos los Artículos en el sistema.\
Pista: Deberá filtrar la consulta principal mediante una subconsulta\
En SQL seria por ejemplo:\
SELECT * FROM articulo art WHERE art.precioventa > (SELECT
AVG(art2.precioventa) FROM articulo art2)

### Ejercicio 14: Explique y ejemplifique la cláusula EXISTS aplicando la misma en el modelo aplicado en el presente trabajo practico  