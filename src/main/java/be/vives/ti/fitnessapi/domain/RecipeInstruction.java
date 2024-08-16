package be.vives.ti.fitnessapi.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Field;

public class RecipeInstruction {
    @NotNull
    @NotEmpty
    @Field("instruction")
    private String instruction;

    @NotNull
    @Min(1)
    @Field("step")
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
