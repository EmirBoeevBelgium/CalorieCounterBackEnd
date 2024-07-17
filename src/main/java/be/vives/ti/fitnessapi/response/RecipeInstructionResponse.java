package be.vives.ti.fitnessapi.response;

import be.vives.ti.fitnessapi.domain.RecipeInstruction;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

public class RecipeInstructionResponse {

    private String instruction;

    private int step;

    public RecipeInstructionResponse(RecipeInstruction recipeInstruction) {
        this.instruction = recipeInstruction.getInstruction();
        this.step = recipeInstruction.getStep();
    }

    public String getInstruction() {
        return instruction;
    }

    public int getStep() {
        return step;
    }

}
