package com.example.networkmodule.singletonNetWork.retrofit.retroFit_modelsExample;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by ddopik on 9/4/2017.
 */
@Root
public class CnnMainModel {

//    @Element(name = "rss")
//    public CnnRss rss;

    @Element(name = "channel")
    public CnnChannelModel channel;





    @Attribute(required = false)
    private String version;
    @Attribute(required = false)
    private String base;


}
