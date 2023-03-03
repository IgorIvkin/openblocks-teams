package ru.openblocks.teams.web.view;

public interface HtmlView {

    String render();

    HtmlView setMainTemplate(String mainTemplate);

    HtmlView setTitle(String title);

    HtmlView setHeader(String headerTemplate);

    HtmlView setFooter(String footerTemplate);

    HtmlView setJavascriptData(String javaScriptData);

    HtmlView addJs(String jsFileName);

    HtmlView addJs(String jsFileName, boolean isModule);

    HtmlView setContent(String contentTemplate);
}

