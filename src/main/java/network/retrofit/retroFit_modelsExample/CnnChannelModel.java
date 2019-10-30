package com.example.networkmodule.singletonNetWork.retrofit.retroFit_modelsExample;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by ddopik on 9/10/2017.
 */
@Root(name = "channel")
public class CnnChannelModel {
    @Element(name = "title")
    public String title;

    @Element(name = "description")
    String description;
    @Element(name = "link")
    String link;
    @Element(name = "language")
    String language;


    @ElementList(name = "item",inline = true)
    List<CnnItem> item;

    public List<CnnItem> getItems() {
        return item;
    }


}
