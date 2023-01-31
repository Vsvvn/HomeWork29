package vs.recipe.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import vs.recipe.model.Recipe;
import vs.recipe.service.RecipeFileService;
import vs.recipe.service.RecipeService;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {
    private static long id = 1;
    private LinkedHashMap<Long, Recipe> recipes = new LinkedHashMap<>();

    final private RecipeFileService recipeFileService;

    public RecipeServiceImpl(RecipeFileService recipeFileService) {
        this.recipeFileService = recipeFileService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public Optional<Recipe> addNewRecipe(Recipe recipe) {
        saveToFile();
        return Optional.ofNullable (recipes.put(id++, recipe));
    }

    @Override
    public Optional<Recipe> getRecipe(Long id) {
        return Optional.ofNullable(recipes.get(id));
    }

    @Override
    public Optional<Recipe> edit(Long id, Recipe recipe) {
        saveToFile();
        return Optional.ofNullable(recipes.replace(id, recipe));
    }

    @Override
    public Optional<Recipe> delete(Long id) {
        return Optional.ofNullable(recipes.remove(id));
    }

    @Override
    public Map<Long, Recipe> getAll() {
        return new HashMap<>(recipes);
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            recipeFileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        try {
            String json = recipeFileService.readFromFile();
            recipes = new ObjectMapper().readValue(json, new TypeReference<LinkedHashMap<Long, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
