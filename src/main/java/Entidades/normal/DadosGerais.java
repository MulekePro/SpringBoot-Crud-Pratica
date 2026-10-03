package Entidades.normal;

import java.time.LocalDate;

public class DadosGerais {

    private String numeroNotificacao;
    private Integer tipoNotificacao;
    private String agravo;
    private String codigoCid10;
    private LocalDate dataNotificacao;
    private String ufNotificacao;
    private String municipioNotificacao;

    public String getNumeroNotificacao() {
        return numeroNotificacao;
    }

    public void setNumeroNotificacao(String numeroNotificacao) {
        this.numeroNotificacao = numeroNotificacao;
    }

    public Integer getTipoNotificacao() {
        return tipoNotificacao;
    }



    public void setTipoNotificacao(Integer tipoNotificacao) {
        this.tipoNotificacao = tipoNotificacao;
    }

    public String getAgravo() {
        return agravo;
    }

    public void setAgravo(String agravo) {
        this.agravo = agravo;
    }

    public String getCodigoCid10() {
        return codigoCid10;
    }

    public void setCodigoCid10(String codigoCid10) {
        this.codigoCid10 = codigoCid10;
    }

    public LocalDate getDataNotificacao() {
        return dataNotificacao;
    }

    public void setDataNotificacao(LocalDate dataNotificacao) {
        this.dataNotificacao = dataNotificacao;
    }

    public String getUfNotificacao() {
        return ufNotificacao;
    }

    public void setUfNotificacao(String ufNotificacao) {
        this.ufNotificacao = ufNotificacao;
    }

    public String getMunicipioNotificacao() {
        return municipioNotificacao;
    }

    public void setMunicipioNotificacao(String municipioNotificacao) {
        this.municipioNotificacao = municipioNotificacao;
    }

    public String getCodigoIbgeMunicipio() {
        return codigoIbgeMunicipio;
    }

    public void setCodigoIbgeMunicipio(String codigoIbgeMunicipio) {
        this.codigoIbgeMunicipio = codigoIbgeMunicipio;
    }

    public String getUnidadeSaude() {
        return unidadeSaude;
    }

    public void setUnidadeSaude(String unidadeSaude) {
        this.unidadeSaude = unidadeSaude;
    }

    public String getCodigoUnidadeSaude() {
        return codigoUnidadeSaude;
    }

    public void setCodigoUnidadeSaude(String codigoUnidadeSaude) {
        this.codigoUnidadeSaude = codigoUnidadeSaude;
    }

    public LocalDate getDataPrimeirosSintomas() {
        return dataPrimeirosSintomas;
    }

    public void setDataPrimeirosSintomas(LocalDate dataPrimeirosSintomas) {
        this.dataPrimeirosSintomas = dataPrimeirosSintomas;
    }

    private String codigoIbgeMunicipio;
    private String unidadeSaude;
    private String codigoUnidadeSaude;
    private LocalDate dataPrimeirosSintomas;
}
