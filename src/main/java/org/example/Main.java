
package org.example;

import funciones.FuncionApp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
            EntityManager em = emf.createEntityManager();

            // Persistir la entidad UnidadMedida en estado "gestionada"
            em.getTransaction().begin();            // Crear una nueva entidad UnidadMedida en estado "nueva"
            UnidadMedida unidadMedida = UnidadMedida.builder()
                    .denominacion("Kilogramo")
                    .build();
            UnidadMedida unidadMedidapote = UnidadMedida.builder()
                    .denominacion("pote")
                    .build();

            em.persist(unidadMedida);
            em.persist(unidadMedidapote);


            // Crear una nueva entidad Categoria en estado "nueva"
            Categoria categoria = Categoria.builder()
                    .denominacion("Frutas")
                    .esInsumo(true)
                    .build();

            // Crear una nueva entidad Categoria en estado "nueva"
            Categoria categoriaPostre = Categoria.builder()
                    .denominacion("Postre")
                    .esInsumo(false)
                    .build();

            // Persistir la entidad Categoria en estado "gestionada"

            em.persist(categoria);
            em.persist(categoriaPostre);


            // Crear una nueva entidad ArticuloInsumo en estado "nueva"
            ArticuloInsumo articuloInsumo = ArticuloInsumo.builder()
                    .denominacion("Manzana")
                    .codigo(Long.toString(new Date().getTime()))
                    .precioCompra(1.5)
                    .precioVenta(5d)
                    .stockActual(100)
                    .stockMaximo(200)
                    .esParaElaborar(true)
                    .unidadMedida(unidadMedida)
                    .build();

            Thread.sleep(1); // Evita duplicación del código

            ArticuloInsumo articuloInsumoPera = ArticuloInsumo.builder()
                    .denominacion("Pera")
                    .codigo(Long.toString(new Date().getTime()))
                    .precioCompra(2.5)
                    .precioVenta(10d)
                    .stockActual(130)
                    .stockMaximo(200)
                    .esParaElaborar(true)
                    .unidadMedida(unidadMedida)
                    .build();

            // Persistir la entidad ArticuloInsumo en estado "gestionada"

            em.persist(articuloInsumo);
            em.persist(articuloInsumoPera);

            Imagen manza1 = Imagen.builder().denominacion("Manzana Verde").
                    build();
            Imagen manza2 = Imagen.builder().denominacion("Manzana Roja").
                    build();

            Imagen pera1 = Imagen.builder().denominacion("Pera Verde").
                    build();
            Imagen pera2 = Imagen.builder().denominacion("Pera Roja").
                    build();


            // Agregar el ArticuloInsumo a la Categoria
            categoria.getArticulos().add(articuloInsumo);
            categoria.getArticulos().add(articuloInsumoPera);
            // Actualizar la entidad Categoria en la base de datos

            em.merge(categoria);

            // Crear una nueva entidad ArticuloManufacturadoDetalle en estado "nueva"
            ArticuloManufacturadoDetalle detalleManzana = ArticuloManufacturadoDetalle.builder()
                    .cantidad(2)
                    .articuloInsumo(articuloInsumo)
                    .build();


            ArticuloManufacturadoDetalle detallePera = ArticuloManufacturadoDetalle.builder()
                    .cantidad(2)
                    .articuloInsumo(articuloInsumoPera)
                    .build();

            // Crear una nueva entidad ArticuloManufacturado en estado "nueva"
            ArticuloManufacturado articuloManufacturado = ArticuloManufacturado.builder()
                    .denominacion("Ensalada de frutas")
                    .descripcion("Ensalada de manzanas y peras ")
                    .precioVenta(150d)
                    .tiempoEstimadoMinutos(10)
                    .preparacion("Cortar las frutas en trozos pequeños y mezclar")
                    .unidadMedida(unidadMedidapote)
                    .build();

            articuloManufacturado.getImagenes().add(manza1);
            articuloManufacturado.getImagenes().add(pera1);

            categoriaPostre.getArticulos().add(articuloManufacturado);
            // Crear una nueva entidad ArticuloManufacturadoDetalle en estado "nueva"

            // Agregar el ArticuloManufacturadoDetalle al ArticuloManufacturado
            articuloManufacturado.getDetalles().add(detalleManzana);
            articuloManufacturado.getDetalles().add(detallePera);

            // Persistir la entidad ArticuloManufacturado en estado "gestionada"
            categoriaPostre.getArticulos().add(articuloManufacturado);
            em.persist(articuloManufacturado);
            em.getTransaction().commit();

            // modificar la foto de manzana roja
            em.getTransaction().begin();
            articuloManufacturado.getImagenes().add(manza2);
            em.merge(articuloManufacturado);
            em.getTransaction().commit();

            //creo y guardo un cliente
            em.getTransaction().begin();
            Cliente cliente = Cliente.builder()
                    .cuit(FuncionApp.generateRandomCUIT())
                    .razonSocial("Juan Perez")
                    .build();
            em.persist(cliente);
            em.getTransaction().commit();

            //creo y guardo una factura
            em.getTransaction().begin();

            FacturaDetalle detalle1 = new FacturaDetalle(3, articuloInsumo);
            detalle1.calcularSubTotal();
            FacturaDetalle detalle2 = new FacturaDetalle(3, articuloInsumoPera);
            detalle2.calcularSubTotal();
            FacturaDetalle detalle3 = new FacturaDetalle(3, articuloManufacturado);
            detalle3.calcularSubTotal();

            Factura factura = Factura.builder()
                    .puntoVenta(2024)
                    .fechaAlta(new Date())
                    .fechaComprobante(FuncionApp.generateRandomDate())
                    .cliente(cliente)
                    .nroComprobante(FuncionApp.generateRandomNumber())
                    .build();
            factura.addDetalleFactura(detalle1);
            factura.addDetalleFactura(detalle2);
            factura.addDetalleFactura(detalle3);
            factura.calcularTotal();

            //Otra factura
            FacturaDetalle detalle4 = new FacturaDetalle(4, articuloManufacturado);
            detalle3.calcularSubTotal();

            Factura factura2 = Factura.builder()
                    .puntoVenta(2024)
                    .fechaAlta(new Date())
                    .fechaComprobante(LocalDate.of(2025, 10, 25))
                    .cliente(cliente)
                    .nroComprobante(FuncionApp.generateRandomNumber())
                    .build();

            factura2.addDetalleFactura(detalle4);
            factura2.calcularTotal();

            em.persist(factura);
            em.persist(factura2);
            em.getTransaction().commit();

            // Crear la consulta SQL nativa
            // Crear la consulta JPQL

            String jpql = "SELECT am FROM ArticuloManufacturado am LEFT JOIN FETCH am.detalles d WHERE am.id = :id";
            Query query = em.createQuery(jpql);
            query.setParameter("id", 3L);
            ArticuloManufacturado articuloManufacturadoCon = (ArticuloManufacturado) query.getSingleResult();

            System.out.println("\nArtículo manufacturado: " + articuloManufacturado.getDenominacion());
            System.out.println("Descripción: " + articuloManufacturado.getDescripcion());
            System.out.println("Tiempo estimado: " + articuloManufacturado.getTiempoEstimadoMinutos() + " minutos");
            System.out.println("Preparación: " + articuloManufacturado.getPreparacion());

            System.out.println("Líneas de detalle:");
            for (ArticuloManufacturadoDetalle detalle : articuloManufacturado.getDetalles()) {
                System.out.println("- " + detalle.getCantidad() + " unidades de " + detalle.getArticuloInsumo().getDenominacion());

            }

            //Consultas JPQL Trabajo Practico
            System.out.println("\n===PROBAMOS CONSULTAS JPQL===");

            System.out.println("\nConsulta 1:");
            String consult1 = "SELECT c FROM Cliente c";
            List<Cliente> clientesList = em.createQuery(consult1, Cliente.class).getResultList();
            for (Cliente clientelist : clientesList) {
                System.out.println("Cliente: " + clientelist.getRazonSocial());
            }

            System.out.println("\nConsulta 2:");
            String consult2 = "SELECT f FROM Factura f WHERE f.fechaComprobante >= :fechaLimite";
            LocalDate fechaLimite = LocalDate.now().minusDays(30);
            List<Factura> facturasUltimoMes = em.createQuery(consult2, Factura.class)
                    .setParameter("fechaLimite", fechaLimite)
                    .getResultList();
            for (Factura facturaUltimoMes : facturasUltimoMes) {
                System.out.println("Facturas del ultimo mes: " + facturaUltimoMes.getFechaComprobante());
            }

            System.out.println("\nConsulta 3:");
            String consult3 = "SELECT f.cliente FROM Factura f GROUP BY f.cliente ORDER BY COUNT(f) DESC";
            Cliente clienteMasFacturado = em.createQuery(consult3, Cliente.class)
                    .setMaxResults(1)
                    .getSingleResult();
            System.out.println("El cliente mas facturado es: "+ clienteMasFacturado);

            System.out.println("\nConsulta 4:");
            String consult4 = """
                SELECT fd.articulo, SUM(fd.cantidad) AS totalVendido
                   FROM FacturaDetalle fd
                   GROUP BY fd.articulo
                   ORDER BY totalVendido DESC
                """;
            List<Object[]> resultados = em.createQuery(consult4, Object[].class).getResultList();
            for (Object[] fila : resultados) {
                Articulo articulo = (Articulo) fila[0];
                double totalVendido = (double) fila[1];
                System.out.println("Producto: " + articulo.getDenominacion() + " - Total vendido: " + totalVendido);
            }

            System.out.println("\nConsulta 5:");

            System.out.println("\nConsulta 6:");

            System.out.println("\nConsulta 7:");

            System.out.println("\nConsulta 8:");
                Long idFactura = factura.getId(); // factura creada antes
                String consulta8 = """
                        SELECT fd.articulo 
                        FROM FacturaDetalle fd 
                        WHERE fd.factura.id = :idFactura 
                        ORDER BY fd.articulo.precioVenta DESC
                    """;
    
                Articulo articuloMasCaro = em.createQuery(consulta8, Articulo.class)
                        .setParameter("idFactura", idFactura)
                        .setMaxResults(1)
                        .getSingleResult();// un unico resultad

                System.out.println("El artículo más caro en la factura con ID " +idFactura + " es :  "
                    +articuloMasCaro.getDenominacion()+ " con precio :  $" +articuloMasCaro.getPrecioVenta());

            
            System.out.println("\nConsulta 9:");
                String consulta9 = "SELECT COUNT(f) FROM Factura f";
                Long totalFacturas = em.createQuery(consulta9, Long.class).getSingleResult();

                System.out.println("Cantidad tot de facturas:  " +totalFacturas);
            
            
            System.out.println("\nConsulta 10:");
                double valorMin = 200;
                String consulta10 = "SELECT f FROM Factura f WHERE f.total > :valor";
                List<Factura> facturasMayores = em.createQuery(consulta10, Factura.class)
                        .setParameter("valor", valorMin)
                        .getResultList();

               System.out.println("Facturas con total mayor a " +valorMin+ "--> :  ");
                for (Factura f : facturasMayores) {
                    System.out.println(" Factura ID: " + f.getId() + " total: $ " + f.getTotal());
                }


            System.out.println("\nConsulta 11:");

            System.out.println("\nConsulta 12:");

            System.out.println("\nConsulta 13:");

            System.out.println("\nConsulta 14:");


            // Cerrar el EntityManager y el EntityManagerFactory
            em.close();
            emf.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
