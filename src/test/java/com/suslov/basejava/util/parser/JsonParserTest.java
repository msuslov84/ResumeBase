package com.suslov.basejava.util.parser;

import com.suslov.basejava.ResumeTestData;
import com.suslov.basejava.model.section.AbstractSection;
import com.suslov.basejava.model.section.ExperienceList;
import com.suslov.basejava.model.section.SkillList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonParserTest {

    @Test
    void serializationSkillListTest() {
        AbstractSection sectionToJson = new SkillList("Skill1, Skill2");
        String json = JsonParser.write(sectionToJson, AbstractSection.class);

        AbstractSection sectionFromJson = JsonParser.read(json, AbstractSection.class);
        assertEquals(sectionToJson, sectionFromJson);
    }

    @Test
    void serializationExperienceListTest() {
        AbstractSection sectionToJson = new ExperienceList(ResumeTestData.fillExperience());
        String json = JsonParser.write(sectionToJson, AbstractSection.class);

        AbstractSection sectionFromJson = JsonParser.read(json, AbstractSection.class);
        assertEquals(sectionToJson, sectionFromJson);
    }
}