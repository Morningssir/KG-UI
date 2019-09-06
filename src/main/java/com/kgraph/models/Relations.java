package com.kgraph.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "relation_set")
public class Relations {
    @Id
    public ObjectId _id;

    @Field("source")
    public int source;

    @Field("target")
    public int target;

    @Field("subject")
    public String subject;

    @Field("predicate")
    public String predicate;

    @Field("object")
    public String object;

    @Field("type")
    public String type;
}
