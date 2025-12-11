package gestaoeventos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "local_evento")
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "morada", length = 255)
    private String morada;

    @Column(name = "capacidade")
    private Integer capacidade;

    @Column(name = "disponibilidade_horaria", length = 100)
    private String disponibilidadeHoraria;

    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;

    @JsonIgnore
    @OneToMany(mappedBy = "local")
    private Set<Evento> eventos = new HashSet<>();

    public Local() {
    }

    // getters e setters

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

    public Set<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(Set<Evento> eventos) {
        this.eventos = eventos;
    }
}
