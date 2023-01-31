package vs.recipe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vs.recipe.service.IngredientFileService;
import vs.recipe.service.RecipeFileService;

import java.io.*;

@RestController
@RequestMapping("/ingredientFiles")
@Tag(name = "Файлы Ингридиентов", description = "работа с файлами списка ингридиентов")
public class IngredientFileController {

    private final IngredientFileService ingredientFileService;


    public IngredientFileController(IngredientFileService ingredientFileService) {
        this.ingredientFileService = ingredientFileService;
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "принимает json-файл с игридиентами и заменяет сохраненный на жестком (локальном) диске файл на новый"
    )
    public ResponseEntity<Void> uploadDataFile(@RequestParam MultipartFile file) {
        ingredientFileService.cleanDataFile();
        File dataFile = ingredientFileService.getDataFile();


        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
