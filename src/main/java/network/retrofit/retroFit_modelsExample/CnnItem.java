package com.example.networkmodule.singletonNetWork.retrofit.retroFit_modelsExample;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by ddopik on 9/10/2017.
 */
@Root(name = "item")
public class CnnItem {


    @Element(name = "title")
    String title;
    @Element( name="description")
    String description;
    @Element(name = "pubDate")
    String pubDate;
    @Element(name = "link")
    String link;
    @Element(name = "guid")
    String guid;
    @Element(name = "creator")
    String creator;
}
