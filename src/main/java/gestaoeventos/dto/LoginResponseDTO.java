package gestaoeventos.dto;

import gestaoeventos.entity.PerfilUtilizador;

public class LoginResponseDTO {

    private Integer numero;
    private String nome;
    private String email;
    private PerfilUtilizador perfil;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(Integer numero, String nome, String email, PerfilUtilizador perfil) {
        this.numero = numero;
        this.nome = nome;
        this.email = email;
        this.perfil = perfil;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PerfilUtilizador getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilUtilizador perfil) {
        this.perfil = perfil;
    }
}
