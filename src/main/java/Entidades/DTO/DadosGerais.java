package Entidades.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosGerais(

        @NotNull(message = "O tipo de notificação")
        Integer tipoNotificacao,

        @NotBlank(message = "O agravo é obrigatório")
        String agravo,

        String codigoCid10,

        @NotNull(message = "A data da notificação ")
        LocalDate dataNotificacao,

        @NotBlank(message = "A UF de notificação")
        String ufNotificacao,

        @NotBlank(message = "O município de notificação ")
        String municipioNotificacao,

        String codigoIbgeMunicipio,

        @NotBlank(message = "A unidade de saúde ")
        String unidadeSaude,

        String codigoUnidadeSaude,

        @NotNull(message = "A data dos primeiros sintomas ")
        LocalDate dataPrimeirosSintomas

) {
}