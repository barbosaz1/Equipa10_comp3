package gestaoeventos.dto;

import gestaoeventos.entity.PerfilUtilizador;

public class UtilizadorCreateDTO {

    private Integer numero;
    private String nome;
    private String email;
    private String password;        // password em claro, só neste DTO
    private PerfilUtilizador perfil;
    private Boolean ativo;          // opcional no update

    public UtilizadorCreateDTO() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PerfilUtilizador getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilUtilizador perfil) {
        this.perfil = perfil;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
