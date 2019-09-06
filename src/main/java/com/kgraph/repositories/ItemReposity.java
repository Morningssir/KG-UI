package com.kgraph.repositories;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.kgraph.models.Items;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemReposity{
    public List<Items> findAll();

    public List<Items> findByItemIndex(int index);

    public JSONArray itemsToArray(List<Items> items);
}
