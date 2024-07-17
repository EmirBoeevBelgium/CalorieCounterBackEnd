package be.vives.ti.fitnessapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class RecipeInstruction {
    @NotNull
    @Column(length = 1000)
    private String instruction;
    @NotNull
    private int step;

    public RecipeInstruction(String instruction, int step) {
        this.instruction = instruction;
        this.step = step;
    }

    protected RecipeInstruction() {

    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
