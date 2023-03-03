package ru.openblocks.teams.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.openblocks.teams.web.view.WebsiteHtmlView;

@Controller
@RequiredArgsConstructor
public class MainPageController {

    @GetMapping({"/", ""})
    public String mainPage(Model model) {
        return WebsiteHtmlView.of(model)
                .setMainTemplate("main-template")
                .setContent("main-page/main-page :: content")
                .setTitle("Главная страница")
                .render();
    }
}
