package gestaoeventos.dto;

public class LocalDTO {

    private Integer id;
    private String nome;
    private String morada;
    private Integer capacidade;
    private String disponibilidadeHoraria;
    private boolean ativo;

    public LocalDTO() {}

    // getters e setters ...
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMorada() {
		return morada;
	}

	public void setMorada(String morada) {
		this.morada = morada;
	}

	public Integer getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(Integer capacidade) {
		this.capacidade = capacidade;
	}

	public String getDisponibilidadeHoraria() {
		return disponibilidadeHoraria;
	}

	public void setDisponibilidadeHoraria(String disponibilidadeHoraria) {
		this.disponibilidadeHoraria = disponibilidadeHoraria;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}


}

