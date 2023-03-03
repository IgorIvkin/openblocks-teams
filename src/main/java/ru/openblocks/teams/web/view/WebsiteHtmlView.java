package ru.openblocks.teams.web.view;

import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Представляет собой настраиваемое HTML-представление для вывода страницы сайта.
 */
public class WebsiteHtmlView implements HtmlView {

    private static final String JAVASCRIPT_KEY = "javascript";

    private static final String JAVASCRIPT_MODULES_KEY = "javascriptModules";

    private static final String TITLE_KEY = "pageTitle";

    private static final String CONTENT_KEY = "contentTemplateName";

    private static final String HEADER_KEY = "headerTemplateName";

    private static final String FOOTER_KEY = "footerTemplateName";

    private static final String JAVASCRIPT_DATA_KEY = "javaScriptData";

    private static final String JAVASCRIPT_EMPTY_DATA = "~{}";

    private final List<String> js = new ArrayList<>();

    private final List<String> jsModules = new ArrayList<>();

    private final Model model;

    private String mainTemplate;

    public WebsiteHtmlView(Model model) {
        this.model = model;
    }

    public static WebsiteHtmlView of(Model model) {
        return new WebsiteHtmlView(model);
    }

    @Override
    public String render() {
        beforeRender();
        return this.mainTemplate;
    }

    @Override
    public HtmlView setMainTemplate(String mainTemplate) {
        this.mainTemplate = mainTemplate;
        return this;
    }

    @Override
    public HtmlView setTitle(String title) {
        putToModel(TITLE_KEY, title);
        return this;
    }

    @Override
    public HtmlView setHeader(String headerTemplate) {
        putToModel(HEADER_KEY, headerTemplate);
        return this;
    }

    @Override
    public HtmlView setFooter(String footerTemplate) {
        putToModel(FOOTER_KEY, footerTemplate);
        return this;
    }

    @Override
    public HtmlView setJavascriptData(String javaScriptData) {
        putToModel(JAVASCRIPT_DATA_KEY, javaScriptData);
        return this;
    }

    @Override
    public HtmlView addJs(String jsFileName) {
        return addJs(jsFileName, false);
    }

    @Override
    public HtmlView addJs(String jsFileName, boolean isModule) {
        if (isModule) {
            jsModules.add(jsFileName);
        } else {
            js.add(jsFileName);
        }
        return this;
    }

    @Override
    public HtmlView setContent(String contentTemplate) {
        putToModel(CONTENT_KEY, contentTemplate);
        return this;
    }

    private HtmlView putToModel(String key, Object value) {
        model.addAttribute(key, value);
        return this;
    }

    private void beforeRender() {
        putToModel(JAVASCRIPT_KEY, js);
        putToModel(JAVASCRIPT_MODULES_KEY, jsModules);
        if (!model.containsAttribute(JAVASCRIPT_DATA_KEY)) {
            putToModel(JAVASCRIPT_DATA_KEY, JAVASCRIPT_EMPTY_DATA);
        }
    }
}
