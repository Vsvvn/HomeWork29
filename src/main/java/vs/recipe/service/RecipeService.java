package vs.recipe.service;

import vs.recipe.model.Recipe;

import java.util.Map;
import java.util.Optional;

public interface RecipeService {
    Optional<Recipe> getRecipe(Long id);
    Optional<Recipe> addNewRecipe(Recipe recipe);
    Optional<Recipe> edit(Long id, Recipe recipe);
    Optional<Recipe> delete(Long id);
    Map<Long, Recipe> getAll();
}
