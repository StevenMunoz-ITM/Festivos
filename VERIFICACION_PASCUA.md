# Verificación de la Fórmula Colombiana de Pascua

## Fórmula Implementada

Para calcular el **Domingo de Ramos** (inicio de Semana Santa):
```
días = d + (2b+4c+6d+5) MOD 7
```

Donde:
- a = Año MOD 19
- b = Año MOD 4  
- c = Año MOD 7
- d = (19a+24) MOD 30

**Domingo de Pascua** = Domingo de Ramos + 7 días

## Verificación con el Ejemplo 1999

Según tu documento:

### Cálculo Manual para 1999:
- a = 1999 MOD 19 = 4
- b = 1999 MOD 4 = 3
- c = 1999 MOD 7 = 4
- d = (19×4 + 24) MOD 30 = 10
- días = 10 + (2×3 + 4×4 + 6×10 + 5) MOD 7 = 13

**Resultado esperado:**
- Domingo de Ramos: 15 marzo + 13 días = **28 marzo 1999**
- Domingo de Pascua: 28 marzo + 7 días = **4 abril 1999**

## Pruebas de la API

### Obtener Domingo de Ramos para 1999
```http
GET /api/festivos/domingo-ramos/1999
```
**Resultado esperado**: `"1999-03-28"`

### Verificar festivos basados en Pascua para 1999
```http
GET /api/festivos/pais/1/pascua
```
Luego calcular las fechas para 1999:
- Jueves Santo: 4 abril - 3 = **1 abril 1999**
- Viernes Santo: 4 abril - 2 = **2 abril 1999**  
- Domingo de Pascua: **4 abril 1999**

### Verificar si el 4 de abril de 1999 es festivo
```http
GET /api/festivos/es-festivo?fecha=1999-04-04&paisId=1
```
**Resultado esperado**: `true`

## Comparación con Algoritmo de Gauss

El algoritmo de Gauss que teníamos anteriormente podría dar resultados diferentes. La fórmula colombiana es más específica y debe ser la que usemos para Colombia.

## Comandos de Prueba

```bash
# Obtener Domingo de Ramos para diferentes años
curl -X GET http://localhost:8080/api/festivos/domingo-ramos/1999
curl -X GET http://localhost:8080/api/festivos/domingo-ramos/2024
curl -X GET http://localhost:8080/api/festivos/domingo-ramos/2025

# Verificar fechas de Pascua calculadas
curl -X GET "http://localhost:8080/api/festivos/es-festivo?fecha=1999-04-04&paisId=1"
curl -X GET "http://localhost:8080/api/festivos/es-festivo?fecha=2024-03-31&paisId=1"

# Verificar Jueves y Viernes Santo para 1999
curl -X GET "http://localhost:8080/api/festivos/es-festivo?fecha=1999-04-01&paisId=1"
curl -X GET "http://localhost:8080/api/festivos/es-festivo?fecha=1999-04-02&paisId=1"
```

## Implementación en el Código

### FestivoService.java
```java
private LocalDate calcularPascua(int año) {
    // Fórmula colombiana
    int a = año % 19;
    int b = año % 4;
    int c = año % 7;
    int d = (19 * a + 24) % 30;
    
    int dias = d + (2 * b + 4 * c + 6 * d + 5) % 7;
    
    LocalDate fechaBase = LocalDate.of(año, 3, 15);
    LocalDate domingoDeRamos = fechaBase.plusDays(dias);
    
    return domingoDeRamos.plusDays(7); // Pascua = Ramos + 7
}

public LocalDate calcularDomingoDeRamos(int año) {
    // Misma fórmula pero solo retorna Domingo de Ramos
    int a = año % 19;
    int b = año % 4;
    int c = año % 7;
    int d = (19 * a + 24) % 30;
    
    int dias = d + (2 * b + 4 * c + 6 * d + 5) % 7;
    
    LocalDate fechaBase = LocalDate.of(año, 3, 15);
    return fechaBase.plusDays(dias);
}
```

### Nuevo Endpoint
```http
GET /api/festivos/domingo-ramos/{año}
```
Retorna la fecha del Domingo de Ramos para cualquier año.