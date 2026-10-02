package Entidades.DTO;

import java.time.LocalDate;

public record DadosGerais(
        Integer tipoNotificacao,
        String agravo,
        String codigoCid10,
        LocalDate dataNotificacao,
        String ufNotificacao,
        String municipioNotificacao,
        String codigoIbgeMunicipio,
        String unidadeSaude,
        String codigoUnidadeSaude,
        LocalDate dataPrimeirosSintomas


) {
}
