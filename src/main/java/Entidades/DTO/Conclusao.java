package Entidades.DTO;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record Conclusao(
        @NotNull(message = "A data da investigação é obrigatória.")
        LocalDate dataInvestigacao,

        String classificacaoFinal,
        String criterioConfirmacaoDescarte,

        String casoAutoctoneResidencia,
        String ufInfeccao,
        String paisInfeccao,
        String municipioInfeccao,
        String distritoInfeccao,
        String bairroInfeccao,

        String doencaRelacionadaTrabalho,
        String evolucaoCaso,
        LocalDate dataObito,
        LocalDate dataEncerramento,

        String observacoesAdicionais,
        String municipioUnidadeSaudeInvestigacao,
        String codUnidadeSaudeInvestigacao,
        String nomeResponsavelInvestigacao,
        String funcaoResponsavelInvestigacao
) {}