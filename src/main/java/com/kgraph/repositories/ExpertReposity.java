package com.kgraph.repositories;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.kgraph.models.Experts;

public interface ExpertReposity {
    public List<Experts> findAll();

    public List<Experts> findByExpertIndex(int index);

    public JSONArray expertsToArray(List<Experts> experts);
}
