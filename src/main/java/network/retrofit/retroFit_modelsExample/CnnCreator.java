package com.example.networkmodule.singletonNetWork.retrofit.retroFit_modelsExample;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by ddopik on 9/10/2017.
 */
@Root(name = "creator")
public class CnnCreator {
    @Element
    String __prefix;
    @Element
    String __text;
}
