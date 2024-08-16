package be.vives.ti.fitnessapi.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

//Request type of the instruction model
public class RecipeInstructionRequest {
    @NotNull
    @NotEmpty
    //@Column(length = 1000)
    private String instruction;
    @NotNull
    @Min(1)
    private int step;

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
