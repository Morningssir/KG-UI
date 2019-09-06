package com.kgraph.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "expert_set")
public class Experts {
    @Id
    public ObjectId _id;

    @Field("name")
    public String name;

    @Field("index")
    public int index;

    public Experts() {};

    public Experts(ObjectId _id, String name, int index) {
        this._id = _id;
        this.name = name;
        this.index = index;
    }

    public String get_id() {
        return _id.toHexString();
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String get_name() { return name; }

    public void set_name(String name) { this.name = name; }

    public int get_index() { return index; }
}
