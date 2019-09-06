package com.kgraph.repositories.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kgraph.repositories.ExpertReposity;
import com.kgraph.models.Experts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service("ExpertService")
public class ExpertService implements ExpertReposity {
    @Autowired
    private MongoOperations mongoOperations;

    public List<Experts> findAll() {
        Query query = new Query();
        return mongoOperations.find(query, Experts.class);
    }

    public List<Experts> findByExpertIndex(int index) {
        Query query = new Query();
        query.addCriteria(Criteria.where("index").is(index));
        return mongoOperations.find(query, Experts.class);
    }

    public JSONArray expertsToArray(List<Experts> experts) {
        JSONArray expertsArray = new JSONArray();
        JSONObject jsonObject = null;

        for(int i = 0; i< experts.size(); i++) {
            jsonObject = new JSONObject();

            jsonObject.put("name", experts.get(i).name);
            jsonObject.put("index", experts.get(i).index);

            expertsArray.add(jsonObject);
        }

        return expertsArray;
    };
}
