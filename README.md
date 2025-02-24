# Analizador Semántico  

## Reglas del Análisis  

El programa analiza el texto escrito en el cuadro de texto (`JText`) y añade los resultados del análisis a las tablas.  
Debe identificar la declaración de Palabras Reservadas, Identificadores/Variables, Literales, la línea en la que se escribieron y los operadores utilizados.  

### Palabras Reservadas  
Las palabras reservadas del analizador son:  
- `ent->` (equivalente a `int`)  
- `rea->` (equivalente a `float`)  
- `cad->` (equivalente a `String`)  

### Identificadores/Variables
La regla de los identificadores/variables es:  
- `[a-zA-Zá-úÁ-Ú][a-zA-Z0-9á-úÁ-Ú]*`
  
### Literales
Detecta valores literales y los asigna correctamente:

- `10` ➝ `ent->`
- `10.5` ➝ `rea->`
- `"Hola"` ➝ `cad->`

## Reglas de Operaciones  

### Enteros  
```
OPA = + | - | *  
Entero = Entero OPA Entero  
```

### Reales  
```
OPA = + | - | * | /  
Real = Entero OPA Entero  
Real = Entero OPA Real  
Real = Real OPA Entero  
```

### Cadenas  
```
OPA = + | -  
Cadena = Cadena OPA Cadena  
Cadena = Cadena OPA Carácter  
Cadena = Carácter OPA Cadena  
Cadena = Carácter OPA Carácter  
```

## Descripción de Errores  
- **Incompatibilidad de tipos `ent->`** (cuando la operación no es compatible con el tipo)  
- **Incompatibilidad de tipos `rea->`** (cuando la operación no es compatible con el tipo)  
- **Incompatibilidad de tipos `cad->`** (cuando la operación no es compatible con el tipo)  
- **Variable Indefinida** (cuando una variable no ha sido definida)  

## Tablas  
Se cuenta con dos tablas:  
1. **Tabla de símbolos (`JTableSimbolos`)** con las columnas:  
   - `Lexema`  
   - `Tipo`  
   - (Las palabras reservadas y operadores dejan en blanco la celda de lexema)  
2. **Tabla de errores (`JTableErrores`)** con las columnas:  
   - `Token Error` (ERSem1, ERSem2, ERSem3, etc.)  
   - `Lexema`  
   - `Línea`  
   - `Descripción`
   - 
## Botones  
Se cuenta con dos botones:  
- **`Limpiar`**: Limpia el `JText` y ambas tablas.  
- **`Analizar`**: Inicia el análisis y limpia los resultados previos.  

---

# Ejemplos de Funcionamiento  

## Entrada Ejemplo 1  
### Entrada en `JText`  
```
ent-> Num1, Num2, Num3;  
rea-> Flotante1, Flotante2;  
Flotante1 = Flotante2 + Num1;  
```
### Acción  
Pulsar botón **"Analizar"**  

### Tabla de Símbolos  
| Lexema      | Tipo    |  
|------------|--------|  
| `ent->`    |        |  
| `Num1`     | ent->  |  
| `Num2`     | ent->  |  
| `Num3`     | ent->  |  
| `rea->`    |        |  
| `Flotante1`| rea->  |  
| `Flotante2`| rea->  |  
| `,`        |        |  
| `;`        |        |  
| `=`        |        |  
| `+`        |        |  

![image](https://github.com/user-attachments/assets/b8ee9a40-bf95-411e-bf07-118c53923a6c)
