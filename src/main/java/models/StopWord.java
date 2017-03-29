package models;

import java.io.Serializable;

public class StopWord implements Serializable {

	private static final long serialVersionUID = -6554016272481637315L;

	private Long id;

	private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
