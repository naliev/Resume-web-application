package com.basejava.webapp.model;

import com.basejava.webapp.util.JsonSectionAdapter;
import com.google.gson.annotations.JsonAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@JsonAdapter(JsonSectionAdapter.class)
public abstract class AbstractSection implements Serializable {
}
