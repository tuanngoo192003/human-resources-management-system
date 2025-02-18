package com.lab.server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Table(name = "positions", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Position implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "position_id", unique = true, nullable = false)
	private int positionId;
	
	@Column(name = "position_name", nullable = false, length = 255)
	private int positionName;
	
}
