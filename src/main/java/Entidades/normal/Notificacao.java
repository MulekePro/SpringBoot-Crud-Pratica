package Entidades.normal;

public class Notificacao {

    private Long id;
    private DadosGerais dadosGerais;
    private NotificacaoIndividual notificacaoIndividual;
    private DadosDeResidencia dadosDeResidencia;
    private Conclusao conclusao;

    public Notificacao() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DadosGerais getDadosGerais() {
        return dadosGerais;
    }

    public void setDadosGerais(DadosGerais dadosGerais) {
        this.dadosGerais = dadosGerais;
    }

    public NotificacaoIndividual getNotificacaoIndividual() {
        return notificacaoIndividual;
    }

    public void setNotificacaoIndividual(NotificacaoIndividual notificacaoIndividual) {
        this.notificacaoIndividual = notificacaoIndividual;
    }

    public DadosDeResidencia getDadosDeResidencia() {
        return dadosDeResidencia;
    }

    public void setDadosDeResidencia(DadosDeResidencia dadosDeResidencia) {
        this.dadosDeResidencia = dadosDeResidencia;
    }

    public Conclusao getConclusao() {
        return conclusao;
    }

    public void setConclusao(Conclusao conclusao) {
        this.conclusao = conclusao;
    }
}