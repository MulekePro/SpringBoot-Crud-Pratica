package Entidades.DTO;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record NotificacaoIndividual(
        @NotBlank(message = "O nome do paciente é obrigatório.")
        String nomePaciente,
        LocalDate dataNascimento,
        String idade,
        @NotBlank(message = "O sexo é obrigatório")
        String sexo,
        String gestante,
        String racaCor,
        String escolaridade,
        String cartaoSus,
        String nomeMae
) {}