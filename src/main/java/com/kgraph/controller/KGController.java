package com.kgraph.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.kgraph.models.Experts;
import com.kgraph.models.Items;
import com.kgraph.models.Relations;
import com.kgraph.repositories.service.ExpertService;
import com.kgraph.repositories.service.ItemService;
import com.alibaba.fastjson.JSONObject;
import com.kgraph.repositories.service.RelationService;
import com.mongodb.client.MongoCursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.kgraph.utils.FileOperate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.InputStream;
import java.util.List;

@Controller
public class KGController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ExpertService expertService;
    @Autowired
    private RelationService relationService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public void showIndexHtml() {}


    @RequestMapping(value = "/findAllItems", method = RequestMethod.GET)
    @ResponseBody
    public String findAllItems() {
        JSONArray itemsArray = new JSONArray();
        List<Items> items = itemService.findAll();

        itemsArray = itemService.itemsToArray(items);

        return itemsArray.toJSONString();
    }

    @RequestMapping(value = "/findItem/{index}", method = RequestMethod.GET)
    @ResponseBody
    public String findByItemName(@PathVariable("index") int index) {
        JSONArray itemsArray = new JSONArray();
        List<Items> items = itemService.findByItemIndex(index);

        itemsArray = itemService.itemsToArray(items);

        return itemsArray.toJSONString();
    }


    @RequestMapping(value = "/findAllExperts", method = RequestMethod.GET)
    @ResponseBody
    public String findAllExperts() {
        JSONArray expertsArray = new JSONArray();
        List<Experts> experts = expertService.findAll();

        expertsArray = expertService.expertsToArray(experts);

        return expertsArray.toJSONString();
    }

    @RequestMapping(value = "/findExpert/{index}", method = RequestMethod.GET)
    @ResponseBody
    public String findByExpertName(@PathVariable("index") int index) {
        JSONArray expertsArray = new JSONArray();
        List<Experts> experts = expertService.findByExpertIndex(index);

        expertsArray = expertService.expertsToArray(experts);

        return expertsArray.toJSONString();
    }


    @RequestMapping(value = "/findAllRelations", method = RequestMethod.GET)
    @ResponseBody
    public String findAllRelations() {
        JSONArray relationsArray = new JSONArray();
        List<Relations> relations = relationService.findAll();

        relationsArray = relationService.relationsToArray(relations);

        return relationsArray.toJSONString();
    }

    @RequestMapping(value = "/findRelationByPatent/{index}", method = RequestMethod.GET)
    @ResponseBody
    public String findRelationById(@PathVariable("index") String index) {
        JSONArray relationsArray = new JSONArray();
        List<Relations> relations = relationService.findRelationByPatentRel(index, "hasExpert");

        relationsArray = relationService.relationsToArray(relations);

        return relationsArray.toJSONString();
    }

    @RequestMapping(value = "/findRelationByExpert/{index}", method = RequestMethod.GET)
    @ResponseBody
    public String findRelationByName(@PathVariable("index") String index) {
        JSONArray relationsArray = new JSONArray();
        List<Relations> relations = relationService.findRelationByExpertRel(index, "hasExpert");

        relationsArray = relationService.relationsToArray(relations);

        return relationsArray.toJSONString();
    }

    @RequestMapping(value = "/findRelationByPatentRel/{index}/{relation}", method = RequestMethod.GET)
    @ResponseBody
    public String findRelationByIdRelation(@PathVariable("index") String index, @PathVariable("relation") String relation) {
        JSONArray relationsArray = new JSONArray();
        List<Relations> relations = relationService.findRelationByPatentRel(index, relation);

        relationsArray = relationService.relationsToArray(relations);

        return relationsArray.toJSONString();
    }

    @RequestMapping(value = "/findRelationByExpertRel/{index}/{relation}", method = RequestMethod.GET)
    @ResponseBody
    public String findRelationByNameRelation(@PathVariable("index") String index, @PathVariable("relation") String relation) {
        JSONArray relationsArray = new JSONArray();
        List<Relations> relations = relationService.findRelationByExpertRel(index, relation);

        relationsArray = relationService.relationsToArray(relations);

        return relationsArray.toJSONString();
    }

    @RequestMapping(value = "/dataSource/type/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String dataSource(@PathVariable("id") String id) {
        JSONObject jsonObject;
        String str = null;
        if(id.equals("nodes")) {
            str = "test.json";
        }
        else if(id.equals("link")) {
            str = "link.json";
        }
        else if(id.equals("part")) {
            str = "NodePartition.json";
        }
        InputStream iostream = KGController.class.getClassLoader().getResourceAsStream("static/data/" + str);
        FileOperate op = new FileOperate();
        str = op.convertStreamToString(iostream);
        jsonObject = JSONObject.parseObject(str);
        return jsonObject.toJSONString();
    }
}
