package vs.recipe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vs.recipe.model.Recipe;
import vs.recipe.service.RecipeService;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/recipe")
@Tag(name = "Рецепты", description = "CRUD-операции и другие эндпоинты для работы с рецептами")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Поиск рецепта по идентификатору",
            description = "Можно искать по одному параметру"
    )
    public ResponseEntity<Recipe> getRecipeId(@PathVariable Long id) {
        return ResponseEntity.of(recipeService.getRecipe(id));
    }

    @PostMapping
    @Operation(
            summary = "Добавление рецепта"
    )
    public Optional<Recipe> addRecipe(@RequestBody Recipe recipe) {
        return recipeService.addNewRecipe(recipe);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Изменение рецепта по идентификатору",
            description = "Можно изменить рецепт с определенным индификатором"
    )
    public ResponseEntity<Recipe> editRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        return ResponseEntity.of(recipeService.edit(id, recipe));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление рецепта по идентификатору",
            description = "Можно удалить рецепт по определенному идентификатору"
    )
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable Long id) {
        return ResponseEntity.of(recipeService.delete(id));
    }

    @GetMapping
    @Operation(
            summary = "Получение списка всех рецептов"
    )
    public Map<Long, Recipe> getAllRecipes() {
        return recipeService.getAll();
    }
}
