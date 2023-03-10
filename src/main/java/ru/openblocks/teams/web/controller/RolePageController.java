package ru.openblocks.teams.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.openblocks.teams.web.view.WebsiteHtmlView;

@Controller
@RequiredArgsConstructor
public class RolePageController {

    @GetMapping("/roles")
    public String addRolePage(Model model) {
        return WebsiteHtmlView.of(model)
                .setMainTemplate("main-template")
                .setContent("roles/add-role :: content")
                .setTitle("Добавить роль пользователю")
                .render();
    }
}
