package com.kgraph.repositories.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kgraph.models.Relations;
import com.kgraph.repositories.RelationReposity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service("RelationService")
public class RelationService implements RelationReposity{
    @Autowired
    private MongoOperations mongoOperations;

    public List<Relations> findAll() {
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is("1"));
        return mongoOperations.find(query, Relations.class);
    }

    public List<Relations> findByRelation(String relation) {
        Query query = new Query();
        query.addCriteria(Criteria.where("predicate").is("www.kg.com/relation/"+relation));
        return mongoOperations.find(query, Relations.class);
    }

    public List<Relations> findRelationByPatentRel(String index, String predicate) {
        Query query = new Query();
        query.addCriteria(Criteria.where("subject").is("www.kg.com/patent/"+index).and("predicate").is("www.kg.com/relation/"+predicate));
        return mongoOperations.find(query, Relations.class);
    }

    public List<Relations> findRelationByExpertRel(String index, String predicate) {

        Criteria c1 = Criteria.where("object").is("www.kg.com/expert/"+index).and("predicate").is("www.kg.com/relation/"+predicate);
        Criteria c2 = Criteria.where("subject").is("www.kg.com/expert/"+index).and("predicate").is("www.kg.com/relation/"+predicate);
        Criteria cr = new Criteria();
        Query query = new Query(cr.orOperator(c1, c2));
        return mongoOperations.find(query, Relations.class);
    }

    public JSONArray relationsToArray(List<Relations> relations) {
        JSONArray relationsArray = new JSONArray();
        JSONObject jsonObject = null;

        for(int i = 0; i< relations.size(); i++) {
            jsonObject = new JSONObject();

            jsonObject.put("source", relations.get(i).source);
            jsonObject.put("target", relations.get(i).target);
            jsonObject.put("subject", relations.get(i).subject);
            jsonObject.put("predicate", relations.get(i).predicate);
            jsonObject.put("object", relations.get(i).object);
            jsonObject.put("type", relations.get(i).type);

            relationsArray.add(jsonObject);
        }

        return relationsArray;
    };
}
