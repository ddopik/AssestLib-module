package com.example.networkmodule.singletonNetWork.retrofit.retroFit_modelsExample;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by ddopik on 9/10/2017.
 */
@Root(name = "guid")
public class CnnGuide {
    @Element
    String _isPermaLink;
    @Element
    String __text;
}
