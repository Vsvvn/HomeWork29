package vs.recipe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Тест")
public class FirstController {


    @GetMapping
    @Operation(
            summary = "Вводное сообщение для приложения"
    )
    public String app() {
        return "Приложение запущено!";
    }

    @GetMapping("/info")
    @Operation(
            summary = "Описание приложения"
    )
    public String appInfo() {
        return "Сясин Владимир, Recipes, 18.01.23, Приложение для кулинарных рецептов.";
    }
}