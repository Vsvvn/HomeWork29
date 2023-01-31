package vs.recipe.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import vs.recipe.model.Ingredient;
import vs.recipe.service.IngredientFileService;
import vs.recipe.service.IngredientService;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {
    final private IngredientFileService ingredientFileService;

    private static long id = 1;
    private LinkedHashMap<Long, Ingredient> ingredients = new LinkedHashMap<>();

    public IngredientServiceImpl(IngredientFileServiceImpl ingredientFileService) {
        this.ingredientFileService = ingredientFileService;
    }


    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public Optional<Ingredient> addNewIngredient(Ingredient ingredient) {
        saveToFile();
        return Optional.ofNullable(ingredients.put(id++, ingredient));

    }

    @Override
    public Optional<Ingredient> getIngredient(Long id) {
        return Optional.ofNullable(ingredients.get(id));
    }

    @Override
    public Optional<Ingredient> editIngredient(Long id, Ingredient ingredient) {
        saveToFile();
        return Optional.ofNullable(ingredients.replace(id, ingredient));
    }

    @Override
    public Optional<Ingredient> deleteIngredient(Long id) {
        return Optional.ofNullable(ingredients.remove(id));
    }

    @Override
    public Map<Long, Ingredient> getAllIngredients() {
        return new HashMap<>(ingredients);
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients);
            ingredientFileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        try {
            String json = ingredientFileService.readFromFile();
            ingredients = new ObjectMapper().readValue(json, new TypeReference<LinkedHashMap<Long, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
