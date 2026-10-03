package Entidades.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosGerais(

        @NotBlank(message = "O número da notificação é obrigatório.")
        String numeroNotificacao,

        @NotNull(message = "O tipo de notificação é obrigatório.")
        Integer tipoNotificacao,

        @NotBlank(message = "O agravo é obrigatório")
        String agravo,

        String codigoCid10,

        @NotNull(message = "A data da notificação é obrigatória.")
        LocalDate dataNotificacao,

        @NotBlank(message = "A UF de notificação é obrigatória.")
        String ufNotificacao,

        @NotBlank(message = "O município de notificação é obrigatório.")
        String municipioNotificacao,

        String codigoIbgeMunicipio,

        @NotBlank(message = "A unidade de saúde é obrigatória.")
        String unidadeSaude,

        String codigoUnidadeSaude,

        @NotNull(message = "A data dos primeiros sintomas é obrigatória.")
        LocalDate dataPrimeirosSintomas

) {
}