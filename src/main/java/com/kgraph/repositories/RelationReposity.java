package com.kgraph.repositories;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.kgraph.models.Relations;

public interface RelationReposity {
    public List<Relations> findAll();

    public List<Relations> findByRelation(String relation);

    public List<Relations> findRelationByPatentRel(String index, String predicate);

    public List<Relations> findRelationByExpertRel(String index, String predicate);

    public JSONArray relationsToArray(List<Relations> relations);
}
