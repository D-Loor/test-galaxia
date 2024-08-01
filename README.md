# PROYECTO GUÍA DEL COMERCIANTE EN LA GALAXIA

## Requisitos

- Java: 17
- maven: ^3.0

## Instalación

1. Clonar el repositorio:

	git clone https://github.com/D-Loor/test-galaxia.git

2. Instalar las dependencias utilizando Maven:

	mvn install

3. Ejecutar la clase main

## Manual de Usuario

1.- Para registrar un alias a un número romano debe ingresar:

    Nombre-Unidad 'es' Número-Romano  Ejemplo: glob es I
    
2.- Para agregar una nueva unidad intergaláctica debe ingresar:

    Alias-Unidad 'es' Cantidad 'Créditos'  Ejemplo: glob glob Silver es 34 Créditos
    
3.- Para calcular una suma de números romanos debe ingresar:

    '¿Cuánto es' Alias1 Alias2 ...'?'  Ejemplo: ¿Cuánto es pish tegj glob glob?
    
4.- Para consultar el crédito de una unidad intergaláctica por alias debe ingresar:

    '¿Cuántos créditos tiene' Alias1 Alias2 ... Unidad-Intergaláctica '?'
    Ejemplo: ¿Cuántos créditos tiene glob prok Silver?
                
### Nota: Se debe tener en cuenta la sintaxis establecida, en caso de cometer un error al escribir una nota se presentará el siguiente mensaje: Nota incorrecta

### Entrada de Prueba:
glob es I

prok es V

pish es X

tegj es L

glob glob Silver es 34 Créditos

glob prok Gold es 57800 Créditos

pish pish Iron es 3910 Créditos

¿Cuánto es pish tegj glob glob?

¿Cuántos créditos tiene glob prok Silver?

¿Cuántos créditos tiene glob prok Gold?

¿Cuántos créditos tiene glob prok Iron?

### Salida de Prueba:
pish tegj glob glob es 42

glob prok Silver es 68 créditos

glob prok Gold es 57800 créditos

glob prok Iron es 782 créditos

No tengo idea de lo que estás hablando

## Créditos

- Diego Loor (https://github.com/D-Loor)

## Contacto

Si tienes alguna pregunta o sugerencia, no dudes en ponerte en contacto conmigo:

- Correo electrónico: diego18.loor@gmail.com
