package com.suslov.basejava.model.section;

import java.util.*;

public class SkillList extends AbstractSection {
    public static final long serialVersionUID = 1L;

    public static final SkillList EMPTY = new SkillList("");

    private List<String> skills = new ArrayList<>();

    public SkillList() {
    }

    public SkillList(String... skills) {
        this(Arrays.asList(skills));
    }

    public SkillList(List<String> skills) {
        this.skills = new ArrayList<>(Objects.requireNonNull(skills, "Error: list of skills must not be null"));
    }

    public List<String> getSkills() {
        return Collections.unmodifiableList(skills);
    }

    public void addSkill(String skill) {
        if (!skill.isBlank()) {
            skills.add(skill.trim());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkillList skillList = (SkillList) o;
        return Objects.equals(skills, skillList.skills);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skills);
    }

    @Override
    public String toString() {
        return skills.toString();
    }
}
