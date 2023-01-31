package vs.recipe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vs.recipe.model.Ingredient;
import vs.recipe.service.IngredientService;

import java.util.Map;

@RestController
@RequestMapping("/ingredient")
@Tag(name = "Ингридиенты", description = "CRUD-операции и другие эндпоинты для работы с ингридиентами")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Поиск ингредиента по идентификатору",
            description = "Можно искать по одному параметру"
    )
    public ResponseEntity<Ingredient> getIngredientId(@PathVariable Long id) {
        return ResponseEntity.of(ingredientService.getIngredient(id));
    }

    @PostMapping
    @Operation(
            summary = "Добавление ингредиента"
    )
    public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient) {
        return ResponseEntity.of(ingredientService.addNewIngredient(ingredient));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Изменение ингредиента по идентификатору",
            description = "Можно изменить ингредиент с определенным идентификатором"
    )
    public ResponseEntity<Ingredient> editIngredient(@PathVariable Long id, @RequestBody Ingredient ingredient) {
        return ResponseEntity.of(ingredientService.editIngredient(id, ingredient));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление ингредиента по идентификатору",
            description = "Можно удалить ингредиент по определенному идентификатору"
    )
    public ResponseEntity<Ingredient> deleteIngredient(@PathVariable Long id) {
        return ResponseEntity.of(ingredientService.deleteIngredient(id));
    }

    @GetMapping
    @Operation(
            summary = "Получение списка всех ингредиентов"
    )
    public Map<Long, Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }
}


