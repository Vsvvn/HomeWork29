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
import vs.recipe.service.RecipeFileService;

import java.io.*;

@RestController
@RequestMapping("/recipeFiles")
@Tag(name = "Файлы Рецептов", description = "работа с файлами списка рецептов")
public class RecipeFileController {

    private final RecipeFileService recipeFileService;


    public RecipeFileController(RecipeFileService recipeFileService) {
        this.recipeFileService = recipeFileService;
    }


    @GetMapping(value = "/export")
    @Operation(
            summary = "Скачать все рецепты"
    )
    public ResponseEntity<InputStreamResource> downLoadDataFile() throws FileNotFoundException {
        File file = recipeFileService.getDataFile();

        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok().
                    contentType(MediaType.APPLICATION_JSON).
                    contentLength(file.length()).
                    header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"RecipeLog.json\"").
                    body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }


    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "принимает json-файл с рецептами и заменяет сохраненный на жестком (локальном) диске файл на новый"
    )
    public ResponseEntity<Void> uploadDataFile(@RequestParam MultipartFile file) {
        recipeFileService.cleanDataFile();
        File dataFile = recipeFileService.getDataFile();


        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
