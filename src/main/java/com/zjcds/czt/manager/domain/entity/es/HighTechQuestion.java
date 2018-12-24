package com.zjcds.czt.manager.domain.entity.es;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * created date：2018-12-23
 * @author niezhegang
 */
@Document(indexName="high_tech_questions",type = "table")
public class HighTechQuestion {
    @Id
    private String id;
    @Field(type = FieldType.String,analyzer = "ik_smart")
    private String question;
    @Field(type = FieldType.String,analyzer = "ik_smart")
    private String answer;
    @Field(type = FieldType.Date)
    private Date addtime;
    @Field(type = FieldType.Date)
    private Date updatedTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}
