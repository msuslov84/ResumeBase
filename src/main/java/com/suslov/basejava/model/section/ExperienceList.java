package com.suslov.basejava.model.section;

import com.suslov.basejava.model.Experience;

import java.util.*;

public class ExperienceList extends AbstractSection {
    public static final long serialVersionUID = 1L;

    private List<Experience> experiences;

    public ExperienceList() {
    }

    public ExperienceList(Experience... experiences) {
        this(Arrays.asList(experiences));
    }

    public ExperienceList(List<Experience> experiences) {
        this.experiences = new ArrayList<>(Objects.requireNonNull(experiences,
                "Error: list of experiences must not be null"));
    }

    public List<Experience> getExperiences() {
        return Collections.unmodifiableList(experiences);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperienceList that = (ExperienceList) o;
        return Objects.equals(experiences, that.experiences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(experiences);
    }

    @Override
    public String toString() {
        return experiences.toString();
    }
}
