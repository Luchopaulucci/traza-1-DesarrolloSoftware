package entidades;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public class Provincia {
    private Long id;
    private String nombre;

    @ToString.Exclude
    private Pais pais;

    @Builder.Default
    private Set<Localidad> localidades = new HashSet<>();
}
