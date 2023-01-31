package vs.recipe.service;

import vs.recipe.model.Ingredient;

import java.util.Map;
import java.util.Optional;

public interface IngredientService {
    Optional<Ingredient> getIngredient(Long id);
    Optional<Ingredient> addNewIngredient(Ingredient ingredient);
    Optional<Ingredient> editIngredient(Long id, Ingredient ingredient);
    Optional<Ingredient> deleteIngredient(Long id);
    Map<Long, Ingredient> getAllIngredients();
}

