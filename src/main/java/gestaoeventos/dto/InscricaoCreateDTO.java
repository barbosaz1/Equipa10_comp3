package gestaoeventos.dto;

public class InscricaoCreateDTO {

    private Integer eventoId;
    private Integer utilizadorNumero;

    public InscricaoCreateDTO() {}

    // getters e setters ...
    
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

    
}