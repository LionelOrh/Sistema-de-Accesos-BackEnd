package com.centroinformacion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Table(name = "estadoMatricula")
public class EstadoMatricula {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstado;

    @OneToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @Column(name = "estado", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0 COMMENT '1: Matriculado, 0: No matriculado'")
    private boolean estado;
}
