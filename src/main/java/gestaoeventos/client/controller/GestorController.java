package gestaoeventos.client.controller;

import gestaoeventos.client.model.UserSession;
import gestaoeventos.client.service.*;
import gestaoeventos.client.util.EventoDialogHelper;
import gestaoeventos.client.util.ToastNotification;
import gestaoeventos.dto.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Window;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controlador do painel do Gestor de Eventos.
 * Permite gerir locais, criar eventos, ver estatísticas e enviar anúncios.
 */
public class GestorController implements Initializable {

    private final LocalClientService localService = new LocalClientService();
    private final EventoService eventoService = new EventoService();
    private final NotificacaoClientService notificacaoService = new NotificacaoClientService();

    // Tabela de locais
    @FXML
    private TableView<LocalDTO> tblLocais;
    @FXML
    private TableColumn<LocalDTO, String> colLocalNome;
    @FXML
    private TableColumn<LocalDTO, Integer> colLocalCapacidade;
    @FXML
    private TableColumn<LocalDTO, String> colLocalMorada;

    // Estatísticas
    @FXML
    private ComboBox<EventoDTO> cmbEventos;
    @FXML
    private GridPane gridEstatisticas;
    @FXML
    private Label lblTotalInscricoes, lblInscricoesAtivas, lblCheckIns, lblVagas, lblOcupacao;

    // Anúncios
    @FXML
    private TextArea txtAnuncio;
    @FXML
    private Label lblResultadoAnuncio;

    // Tabela de eventos
    @FXML
    private TableView<EventoDTO> tblEventos;
    @FXML
    private TableColumn<EventoDTO, String> colEventoTitulo;
    @FXML
    private TableColumn<EventoDTO, String> colEventoData;
    @FXML
    private TableColumn<EventoDTO, String> colEventoEstado;
    @FXML
    private TableColumn<EventoDTO, Void> colEventoAcoes;

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupLocaisTable();
        setupEventosTable();
        carregarLocais();
        carregarEventos();
        carregarComboEventos();
    }

    private void setupLocaisTable() {
        colLocalNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colLocalCapacidade.setCellValueFactory(new PropertyValueFactory<>("capacidade"));
        colLocalMorada.setCellValueFactory(new PropertyValueFactory<>("morada"));
    }

    private void setupEventosTable() {
        colEventoTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colEventoData.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getDataInicio() != null ? c.getValue().getDataInicio().format(DTF) : ""));
        colEventoEstado.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getEstado() != null ? c.getValue().getEstado().toString() : ""));

        if (colEventoAcoes != null) {
            colEventoAcoes.setCellFactory(col -> new TableCell<>() {
                private final Button btnEditar = new Button("✏️");
                private final Button btnApagar = new Button("🗑️");
                private final HBox hbox = new HBox(5, btnEditar, btnApagar);

                {
                    btnEditar.getStyleClass().add("btn-icon");
                    btnApagar.getStyleClass().add("btn-icon");
                    btnApagar.setStyle("-fx-text-fill: #ef4444;");

                    btnEditar.setOnAction(e -> editarEvento(getTableView().getItems().get(getIndex())));
                    btnApagar.setOnAction(e -> apagarEvento(getTableView().getItems().get(getIndex())));
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : hbox);
                }
            });
        }
    }

    @FXML
    public void carregarLocais() {
        try {
            List<LocalDTO> locais = localService.listarTodos();
            tblLocais.setItems(FXCollections.observableArrayList(locais));
        } catch (Exception e) {
            mostrarErro("Erro ao carregar locais: " + e.getMessage());
        }
    }

    @FXML
    public void novoLocal() {
        mostrarInfo("Para criar novos locais, utilize o painel Admin.");
    }

    @FXML
    public void carregarEventos() {
        if (!UserSession.getInstance().isLoggedIn())
            return;
        try {
            Integer numero = UserSession.getInstance().getUser().getNumero();
            List<EventoDTO> eventos = eventoService.listarPorOrganizador(numero);
            tblEventos.setItems(FXCollections.observableArrayList(eventos));
        } catch (Exception e) {
            mostrarErro("Erro ao carregar eventos: " + e.getMessage());
        }
    }

    private void carregarComboEventos() {
        try {
            List<EventoDTO> eventos = eventoService.listarTodos();
            cmbEventos.setItems(FXCollections.observableArrayList(eventos));
            cmbEventos.setConverter(new javafx.util.StringConverter<>() {
                @Override
                public String toString(EventoDTO e) {
                    return e != null ? e.getTitulo() : "";
                }

                @Override
                public EventoDTO fromString(String s) {
                    return null;
                }
            });
        } catch (Exception e) {
            mostrarErro("Erro ao carregar lista de eventos: " + e.getMessage());
        }
    }

    @FXML
    public void criarEvento() {
        if (!UserSession.getInstance().isLoggedIn()) {
            mostrarErro("Deve estar autenticado para criar eventos.");
            return;
        }

        try {
            List<LocalDTO> locais = localService.listarTodos();
            if (locais.isEmpty()) {
                mostrarAviso("Não existem locais disponíveis. Contacte o administrador.");
                return;
            }

            Integer criadorNumero = UserSession.getInstance().getUser().getNumero();
            Optional<EventoCreateDTO> resultado = EventoDialogHelper.mostrarDialogoCriarEvento(locais, criadorNumero);

            resultado.ifPresent(dto -> {
                EventoDTO eventoCriado = eventoService.criar(dto);
                if (eventoCriado != null) {
                    mostrarSucesso("Evento '" + eventoCriado.getTitulo() + "' criado com sucesso!");
                    carregarEventos();
                    carregarComboEventos();
                } else {
                    mostrarErro("Falha ao criar o evento. Verifique os dados e tente novamente.");
                }
            });
        } catch (Exception e) {
            mostrarErro("Erro ao criar evento: " + e.getMessage());
        }
    }

    private void editarEvento(EventoDTO evento) {
        if (!UserSession.getInstance().isLoggedIn())
            return;

        try {
            List<LocalDTO> locais = localService.listarTodos();
            Integer gestorNumero = UserSession.getInstance().getUser().getNumero();

            Optional<EventoCreateDTO> resultado = EventoDialogHelper.mostrarDialogoEditarEvento(
                    evento, locais, gestorNumero);

            resultado.ifPresent(dto -> {
                EventoDTO atualizado = eventoService.atualizar(evento.getId(), dto);
                if (atualizado != null) {
                    mostrarSucesso("Evento '" + atualizado.getTitulo() + "' atualizado com sucesso!");
                    carregarEventos();
                    carregarComboEventos();
                } else {
                    mostrarErro("Falha ao atualizar o evento.");
                }
            });
        } catch (Exception e) {
            mostrarErro("Erro ao editar evento: " + e.getMessage());
        }
    }

    private void apagarEvento(EventoDTO evento) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar Eliminação");
        confirm.setHeaderText("Apagar evento: " + evento.getTitulo());
        confirm.setContentText("Esta ação não pode ser revertida. Tem a certeza?");

        confirm.showAndWait().filter(r -> r == ButtonType.OK).ifPresent(r -> {
            if (eventoService.apagar(evento.getId())) {
                mostrarSucesso("Evento eliminado com sucesso!");
                carregarEventos();
                carregarComboEventos();
            } else {
                mostrarErro("Falha ao eliminar o evento.");
            }
        });
    }

    @FXML
    public void verEstatisticas() {
        EventoDTO selected = cmbEventos.getValue();
        if (selected == null) {
            mostrarAviso("Selecione um evento.");
            return;
        }

        try {
            EstatisticasEventoDTO stats = eventoService.obterEstatisticas(selected.getId());
            if (stats != null) {
                gridEstatisticas.setVisible(true);
                lblTotalInscricoes.setText(String.valueOf(stats.getTotalInscricoes()));
                lblInscricoesAtivas.setText(String.valueOf(stats.getInscricoesAtivas()));
                lblCheckIns.setText(String.valueOf(stats.getCheckInsRealizados()));
                lblVagas.setText(
                        stats.getVagasDisponiveis() >= 0 ? String.valueOf(stats.getVagasDisponiveis()) : "Ilimitado");
                lblOcupacao.setText(String.format("%.1f%%", stats.getPercentualOcupacao()));
                mostrarSucesso("Estatísticas carregadas com sucesso!");
            } else {
                mostrarErro("Não foi possível obter estatísticas.");
            }
        } catch (Exception e) {
            mostrarErro("Erro ao obter estatísticas: " + e.getMessage());
        }
    }

    @FXML
    public void enviarAnuncio() {
        String conteudo = txtAnuncio.getText();
        if (conteudo == null || conteudo.isBlank()) {
            mostrarAviso("Escreva o conteúdo do anúncio.");
            return;
        }

        if (!UserSession.getInstance().isLoggedIn())
            return;

        try {
            Integer autorNumero = UserSession.getInstance().getUser().getNumero();
            String resultado = notificacaoService.enviarAnuncioBroadcast(conteudo, autorNumero);
            lblResultadoAnuncio.setText(resultado);
            txtAnuncio.clear();
            mostrarSucesso("Anúncio enviado com sucesso!");
        } catch (Exception e) {
            mostrarErro("Erro ao enviar anúncio: " + e.getMessage());
        }
    }

    // Métodos de notificação
    private Window getWindow() {
        return tblLocais.getScene() != null ? tblLocais.getScene().getWindow() : null;
    }

    private void mostrarSucesso(String mensagem) {
        Window window = getWindow();
        if (window != null)
            ToastNotification.sucesso(window, mensagem);
    }

    private void mostrarErro(String mensagem) {
        Window window = getWindow();
        if (window != null)
            ToastNotification.erro(window, mensagem);
    }

    private void mostrarAviso(String mensagem) {
        Window window = getWindow();
        if (window != null)
            ToastNotification.aviso(window, mensagem);
    }

    private void mostrarInfo(String mensagem) {
        Window window = getWindow();
        if (window != null)
            ToastNotification.info(window, mensagem);
    }
}
