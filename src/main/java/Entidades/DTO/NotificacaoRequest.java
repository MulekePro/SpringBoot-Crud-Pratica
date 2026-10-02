package Entidades.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record NotificacaoRequest(
        @NotNull(message = "Os dados gerais são obrigatórios.")
        @Valid
        DadosGerais dadosGerais,

        @NotNull(message = "A notificação individual é obrigatória.")
        @Valid
        NotificacaoIndividual notificacaoIndividual,

        @NotNull(message = "Os dados de residência são obrigatórios.")
        @Valid
        DadosDeResidencia dadosDeResidencia,

        @NotNull(message = "Os dados de conclusão são obrigatórios.")
        @Valid
        Conclusao conclusao
) {}