package Entidades.DTO;

public record DadosDeResidencia(

        String uf,
        String municipio,
        String distrito,
        String bairro,
        String logradouro,
        String numero,
        String complemento,
        String geoCampo1,
        String geoCampo2,
        String pontoReferencia,
        String cep,
        String telefone,
        String zona,
        String pais
) {
}
