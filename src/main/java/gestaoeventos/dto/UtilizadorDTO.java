package gestaoeventos.dto;

import gestaoeventos.entity.PerfilUtilizador;

/**
 * DTO público para representar um utilizador na API.
 * Apenas os campos que podem ser expostos externamente.
 */

public class UtilizadorDTO {

    // Número identificador do utilizador
    private Integer numero;

    // Nome do utilizador.
    private String nome;

    // Email do utilizador (único)
    private String email;

    // Perfil/role do utilizador (enum)
    private PerfilUtilizador perfil;

    // Indica se a conta está activa ou não.
    private boolean ativo;

    // Construtor
    public UtilizadorDTO(Integer numero, String nome, String email,
                         PerfilUtilizador perfil, boolean ativo) {
        this.numero = numero;
        this.nome = nome;
        this.email = email;
        this.perfil = perfil;
        this.ativo = ativo;
    }

    // GETTERS E SETTERS

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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}