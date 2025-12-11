
package gestaoeventos.dto;

import java.time.LocalDateTime;

public class ListaEsperaDTO {

    private Integer id;
    private Integer eventoId;
    private Integer utilizadorNumero;
    private LocalDateTime dataEntrada;
    private Integer posicao;

    public ListaEsperaDTO() {}

    // getters e setters ...
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEventoId() {
		return eventoId;
	}

	public void setEventoId(Integer eventoId) {
		this.eventoId = eventoId;
	}

	public Integer getUtilizadorNumero() {
		return utilizadorNumero;
	}

	public void setUtilizadorNumero(Integer utilizadorNumero) {
		this.utilizadorNumero = utilizadorNumero;
	}

	public LocalDateTime getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(LocalDateTime dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public Integer getPosicao() {
		return posicao;
	}

	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}    
}

