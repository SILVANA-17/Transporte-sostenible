## Requerimientos 

La empresa EcoRide S.A.S., dedicada al alquiler y gestión de medios de transporte sostenibles, requiere el desarrollo de un API REST para administrar su plataforma de vehículos eléctricos y bicicletas. El sistema debe permitir registrar, consultar, actualizar y eliminar información de los diferentes actores del negocio, garantizando la aplicación de los principios de la Programación Orientada a Objetos (POO) y utilizando Java (versión superior a 23) con el framework Spring Boot y Lombok.

Clases base del modelo
1.	Vehiculo (abstracta)
      Clase padre de todos los vehículos. Contiene atributos comunes como id, marca, modelo, precioPorHora.
2.	BicicletaElectrica (hereda de Vehiculo)
      Clase concreta que representa bicicletas eléctricas.
      3.MotoElectrica (hereda de Vehiculo)
      Clase concreta que representa motocicletas eléctricas.
      4.Usuario (abstracta)
      Clase padre de los usuarios registrados. Tiene atributos como id, nombre, correo.
      5.Cliente (hereda de Usuario)
      Representa a los clientes que alquilan vehículos.
      6.Empleado (hereda de Usuario)
      Representa al personal encargado del mantenimiento o gestión de alquileres.
      7.ContratoAlquiler (clase concreta – composición de Vehiculo y Cliente)
      Representa el contrato generado cuando un cliente alquila un vehículo. Contiene fechas, precio total y estado.
      8.Factura (record)
      Representa la factura emitida por cada contrato de alquiler. Inmutable, con datos como idFactura, fecha, valorTotal.
      9.MetodoPago (enum)
      Enumerador con valores como EFECTIVO, TARJETA, TRANSFERENCIA, CRYPTO.
      10.Mantenimiento (interfaz)
      Define los métodos que deben implementar los vehículos que requieren revisiones técnicas (realizarMantenimiento(), obtenerProximoMantenimiento()).
