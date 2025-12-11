package gestaoeventos.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inscricao",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_inscricao_utilizador_evento",
                columnNames = {"utilizador_numero", "evento_id"}
        )
)
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @ManyToOne(optional = false)
    @JoinColumn(name = "utilizador_numero", nullable = false)
    private Utilizador utilizador;

    @Column(name = "data_inscricao", nullable = false)
    private LocalDateTime dataInscricao = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoInscricao estado = EstadoInscricao.ATIVA;

    @Column(name = "check_in", nullable = false)
    private boolean checkIn = false;

    @Column(name = "data_checkin")
    private LocalDateTime dataCheckin;

    @Column(name = "qr_code_checkin", length = 100)
    private String qrCodeCheckin;

    @Column(name = "validade_qrcode")
    private LocalDateTime validadeQrcode;

    public Inscricao() {}

    // GETTERS & SETTERS 

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Utilizador getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(Utilizador utilizador) {
        this.utilizador = utilizador;
    }

    public LocalDateTime getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(LocalDateTime dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public EstadoInscricao getEstado() {
        return estado;
    }

    public void setEstado(EstadoInscricao estado) {
        this.estado = estado;
    }

    public boolean isCheckIn() {
        return checkIn;
    }

    public void setCheckIn(boolean checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getDataCheckin() {
        return dataCheckin;
    }

    public void setDataCheckin(LocalDateTime dataCheckin) {
        this.dataCheckin = dataCheckin;
    }

    public String getQrCodeCheckin() {
        return qrCodeCheckin;
    }

    public void setQrCodeCheckin(String qrCodeCheckin) {
        this.qrCodeCheckin = qrCodeCheckin;
    }

    public LocalDateTime getValidadeQrcode() {
        return validadeQrcode;
    }

    public void setValidadeQrcode(LocalDateTime validadeQrcode) {
        this.validadeQrcode = validadeQrcode;
    }
}
