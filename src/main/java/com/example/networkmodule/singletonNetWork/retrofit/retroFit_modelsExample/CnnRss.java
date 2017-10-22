package com.example.networkmodule.singletonNetWork.retrofit.retroFit_modelsExample;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by ddopik on 9/10/2017.
 */
@Root(name = "rss")
public class CnnRss {

//    @Element(name = "_version")
//    public String _version;
//    @Element(name = "_xmlns")
//    public String _xmlns;
//    @Element(name = "_xml")
//    public String _xml;




    @Attribute(required = false)
    private String version;
    @Attribute(required = false)
    private String base;
}
