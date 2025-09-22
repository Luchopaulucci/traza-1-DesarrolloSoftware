package entidades;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public class Pais {
    private Long id;
    private String nombre;
    
    @Builder.Default
    private List<Provincia> provincias = new ArrayList<>();
}
