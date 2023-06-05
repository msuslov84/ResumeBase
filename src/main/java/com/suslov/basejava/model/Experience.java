package com.suslov.basejava.model;

import com.suslov.basejava.util.adapter.LocalDateXmlAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.suslov.basejava.util.DateUtil.NOW;

@XmlAccessorType(XmlAccessType.FIELD)
public class Experience implements Serializable {
    public static final long serialVersionUID = 1L;

    private Link homePage;
    private List<Period> periods;

    public Experience() {
    }

    public Experience(String homePage, String title, List<Period> periods) {
        this.homePage = new Link(title, homePage);
        this.periods = periods;
    }

    public Experience(String homePage, String title, Period... periods) {
        this(homePage, title, Arrays.asList(periods));
    }

    public void setHomePage(Link homePage) {
        this.homePage = homePage;
    }

    public Link getHomePage() {
        return homePage;
    }

    public void addPeriod(Period period) {
        periods.add(period);
    }

    public List<Period> getPeriods() {
        return Collections.unmodifiableList(periods);
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Period implements Serializable {
        public static final long serialVersionUID = 1L;

        private String title;
        @XmlJavaTypeAdapter(LocalDateXmlAdapter.class)
        private LocalDate periodFrom;
        @XmlJavaTypeAdapter(LocalDateXmlAdapter.class)
        private LocalDate periodTo;
        private String description;

        public Period() {
        }

        public Period(String title, LocalDate periodFrom, String description) {
            this(title, periodFrom, NOW, description);
        }

        public Period(String title, LocalDate periodFrom, LocalDate periodTo, String description) {
            this.title = Objects.requireNonNull(title, "Title must not be null");
            this.periodFrom = Objects.requireNonNull(periodFrom, "Begin of period must not be null");
            this.periodTo = Objects.requireNonNull(periodTo, "End of period must not be null");
            this.description = Objects.requireNonNullElse(description, "");
        }

        public String getTitle() {
            return title;
        }

        public LocalDate getPeriodFrom() {
            return periodFrom;
        }

        public LocalDate getPeriodTo() {
            return periodTo;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Period period = (Period) o;
            return Objects.equals(title, period.title) && Objects.equals(periodFrom, period.periodFrom) &&
                    Objects.equals(periodTo, period.periodTo) && Objects.equals(description, period.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, periodFrom, periodTo, description);
        }

        @Override
        public String toString() {
            return "\n{" +
                    "title='" + title + '\'' +
                    ", from=" + periodFrom +
                    ", to=" + periodTo +
                    ", description='" + description + '\'' +
                    "}";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        return Objects.equals(homePage, that.homePage) && Objects.equals(periods, that.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, periods);
    }

    @Override
    public String toString() {
        return "Experience{\nhomePage=" + homePage +
                ", \nperiods:" + periods + "}\n";
    }
}
