package com.suslov.basejava.model;

public enum ContactType {
    TELEPHONE("Телефон") {
        @Override
        protected String toHtmlNotNull(String value) {
            return getTitle() + ": " + value;
        }
    },
    SKYPE("Скайп") {
        @Override
        public String toHtmlNotNull(String value) {
            return toLink("skype:" + value, value);
        }
    },
    EMAIL("Эл. почта") {
        @Override
        public String toHtmlNotNull(String value) {
            return toLink("mailto:" + value, value);
        }
    },
    LINKEDIN("LinkedIn") {
        @Override
        protected String toHtmlNotNull(String value) {
            return toLink(value);
        }
    },
    GITHUB("GitHub") {
        @Override
        protected String toHtmlNotNull(String value) {
            return toLink(value);
        }
    },
    STACKOVERFLOW("StackOverFlow") {
        @Override
        protected String toHtmlNotNull(String value) {
            return toLink(value);
        }
    },
    HOMEPAGE("Домашняя страница") {
        @Override
        protected String toHtmlNotNull(String value) {
            return toLink(value);
        }
    };

    private final String title;

    protected abstract String toHtmlNotNull(String value);

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtmlNotNull(value);
    }

    protected String toLink(String href) {
        return toLink(href, title);
    }

    protected String toLink(String href, String title) {
        return "<a href='" + href +  "'>" + title + "</a>";
    }
}
