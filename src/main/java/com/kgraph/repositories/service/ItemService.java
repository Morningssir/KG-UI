package com.kgraph.repositories.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kgraph.models.Items;
import com.kgraph.repositories.ItemReposity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service("ItemService")
public class ItemService implements ItemReposity {
    @Autowired
    private MongoOperations mongoOperations;

    public List<Items> findAll() {
        Query query = new Query();
        return mongoOperations.find(query, Items.class);
    }

    public List<Items> findByItemIndex(int index) {
        Query query = new Query();
        query.addCriteria(Criteria.where("index").is(index));
        return mongoOperations.find(query, Items.class);
    }

    public JSONArray itemsToArray(List<Items> items) {
        JSONArray itemsArray = new JSONArray();
        JSONObject jsonObject = null;

        for(int i = 0; i< items.size(); i++) {
            jsonObject = new JSONObject();

            jsonObject.put("id", items.get(i).id);
            jsonObject.put("name", items.get(i).name);
            jsonObject.put("date", items.get(i).date);
            jsonObject.put("summary", items.get(i).summary);
            jsonObject.put("index", items.get(i).index);

            itemsArray.add(jsonObject);
        }

        return itemsArray;
    };
}
