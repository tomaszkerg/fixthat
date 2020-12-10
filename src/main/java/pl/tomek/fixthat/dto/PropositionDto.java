package pl.tomek.fixthat.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PropositionDto {
    private Long id;
    @NotNull
    private double price;
    private String username;
    private Long orderId;
    private int days;
}
