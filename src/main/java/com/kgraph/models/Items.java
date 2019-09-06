package com.kgraph.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "item_set")
public class Items {
    @Id
    public ObjectId _id;

    @Field("id")
    public String id;

    @Field("name")
    public String name;

    @Field("summary")
    public String summary;

    @Field("date")
    public String date;

    @Field("index")
    public int index;

    public Items() {}

    public Items(ObjectId _id, String id, String name, String summary, String date, int index) {
        this._id = _id;
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.date = date;
        this.index = index;
    }

    public String get_id() {
        return _id.toHexString();
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String get_pid() { return id; }

    public void set_pid(String id) { this.id = id; }

    public String get_name() { return name; }

    public void set_name(String name) { this.name = name; }

    public String get_summary() { return summary; }

    public void set_summary(String summary) { this.summary = summary; }

    public String get_date() { return date; }

    public void set_date(String date) { this.date = date; }

    public int get_index() { return index; }

}
