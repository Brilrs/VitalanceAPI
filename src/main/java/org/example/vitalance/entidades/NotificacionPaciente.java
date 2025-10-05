package org.example.vitalance.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Entity @Table(name = "notificaciones_paciente",
        indexes = {
                @Index(name="idx_notif_pac_tipo_fecha", columnList = "id_paciente,tipo_indicador,creada_en")
        })
@NoArgsConstructor @AllArgsConstructor
public class NotificacionPaciente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotificacion;

    @Column(length = 60, nullable = false)
    private String tipoIndicador;         // "GLUCOSA"

    private Double valor;                 // valor medido
    @Column(length = 20) private String unidad; // "mg/dL"

    @Column(length = 12, nullable = false)
    private String severidad;             // "CRITICA" | "ADVERTENCIA"

    @Column(length = 12, nullable = false)
    private String estado = "ENVIADA";    // "ENVIADA" | "VISTA" | "COMPARTIDA"

    @Column(length = 180, nullable = false)
    private String mensaje;               // ej: "Glucosa 45 mg/dL fuera de rango (70–180)"

    @Column(length = 180)
    private String recomendacion;         // ej: "Tomar azúcar de acción rápida"

    private LocalDateTime creadaEn = LocalDateTime.now();
    private LocalDateTime actualizadaEn;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_medicion", nullable = false)
    private Mediciones medicion;          // para “acceder al detalle”
}
