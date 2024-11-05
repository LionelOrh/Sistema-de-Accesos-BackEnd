package com.centroinformacion.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Table(name = "usuario_tiene_proveedor")
public class UsuarioHasProveedor {
	@EmbeddedId
	private UsuarioHasProveedorPK usuarioHasRolPK;
	
	@ManyToOne
	@JoinColumn(name = "idUsuarioSupervisor", nullable = false, insertable = false,updatable =false)
	private Usuario usuarioSupervisor;
	
	@ManyToOne
	@JoinColumn(name = "idProveedor", nullable = false, insertable = false,updatable =false)
	private Proveedor proveedor;
	
}
