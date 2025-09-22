import entidades.*;
import repositorio.InMemoryRepository;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        InMemoryRepository<Empresa> empresaRepo = new InMemoryRepository<>();

        // País
        Pais argentina = Pais.builder().id(1L).nombre("Argentina").build();

        // Provincias
        Provincia buenosAires = Provincia.builder().id(1L).nombre("Buenos Aires").pais(argentina).build();
        Provincia cordoba = Provincia.builder().id(2L).nombre("Córdoba").pais(argentina).build();
        argentina.getProvincias().add(buenosAires);
        argentina.getProvincias().add(cordoba);

        // Localidades
        Localidad caba = Localidad.builder().id(1L).nombre("CABA").provincia(buenosAires).build();
        Localidad laPlata = Localidad.builder().id(2L).nombre("La Plata").provincia(buenosAires).build();
        Localidad cordobaCapital = Localidad.builder().id(3L).nombre("Córdoba Capital").provincia(cordoba).build();
        Localidad villaCarlosPaz = Localidad.builder().id(4L).nombre("Villa Carlos Paz").provincia(cordoba).build();

        // Domicilios
        Domicilio domCaba = Domicilio.builder().id(1L).calle("Av. Rivadavia").numero(4500).cp(1406).localidad(caba).build();
        Domicilio domLaPlata = Domicilio.builder().id(2L).calle("Diagonal 74").numero(120).cp(1900).localidad(laPlata).build();
        Domicilio domCordobaCap = Domicilio.builder().id(3L).calle("Av. Colón").numero(800).cp(5000).localidad(cordobaCapital).build();
        Domicilio domVCP = Domicilio.builder().id(4L).calle("Av. Cárcano").numero(250).cp(5152).localidad(villaCarlosPaz).build();
        // Sucursales
        Sucursal suc1 = Sucursal.builder().id(1L).nombre("Sucursal CABA").horarioApertura(LocalTime.of(9, 0)).horarioCierre(LocalTime.of(18, 0)).esCasaMatriz(true).domicilio(domCaba).build();
        Sucursal suc2 = Sucursal.builder().id(2L).nombre("Sucursal La Plata").horarioApertura(LocalTime.of(9, 0)).horarioCierre(LocalTime.of(18, 0)).esCasaMatriz(false).domicilio(domLaPlata).build();
        Sucursal suc3 = Sucursal.builder().id(3L).nombre("Sucursal Córdoba Capital").horarioApertura(LocalTime.of(8, 30)).horarioCierre(LocalTime.of(17, 30)).esCasaMatriz(true).domicilio(domCordobaCap).build();
        Sucursal suc4 = Sucursal.builder().id(4L).nombre("Sucursal Villa Carlos Paz").horarioApertura(LocalTime.of(8, 30)).horarioCierre(LocalTime.of(17, 30)).esCasaMatriz(false).domicilio(domVCP).build();

        // Empresas
        Empresa empresa1 = Empresa.builder()
                .id(1L).nombre("Empresa 1").razonSocial("Empresa Uno SRL").cuit("20300123456")
                .sucursales(new HashSet<>(Set.of(suc1, suc2)))
                .build();

        Empresa empresa2 = Empresa.builder()
                .id(2L).nombre("Empresa 2").razonSocial("Empresa Dos SA").cuit("27300987654")
                .sucursales(new HashSet<>(Set.of(suc3, suc4)))
                .build();

        empresaRepo.save(empresa1);
        empresaRepo.save(empresa2);

        // Mostrar todas las empresas
        System.out.println("=== TODAS LAS EMPRESAS ===");
        empresaRepo.findAll().forEach(System.out::println);

        // Buscar por ID
        System.out.println("\n=== BUSCAR EMPRESA POR ID 1 ===");
        empresaRepo.findById(1L).ifPresent(System.out::println);

        // Buscar por nombre
        System.out.println("\n=== BUSCAR EMPRESA POR NOMBRE 'Empresa 2' ===");
        empresaRepo.findByField("nombre", "Empresa 2").forEach(System.out::println);

        // Actualizar empresa
        Empresa empresaActualizada = Empresa.builder()
                .id(1L).nombre("Empresa 1 Actualizada").razonSocial("Empresa Uno SRL Actualizada").cuit("20111111111")
                .sucursales(empresa1.getSucursales())
                .build();
        empresaRepo.update(1L, empresaActualizada);

        System.out.println("\n=== EMPRESA 1 DESPUÉS DE ACTUALIZACIÓN ===");
        empresaRepo.findById(1L).ifPresent(System.out::println);

        // Eliminar empresa
        empresaRepo.delete(2L);
        System.out.println("\n=== EMPRESAS DESPUÉS DE ELIMINAR ID 2 ===");
        empresaRepo.findAll().forEach(System.out::println);
    }
}
