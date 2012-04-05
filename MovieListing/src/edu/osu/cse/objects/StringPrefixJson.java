package edu.osu.cse.objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement
public class StringPrefixJson {
	String prefix;

	public StringPrefixJson() {
		super();
	}

	public StringPrefixJson(String prefix) {
		super();
		this.prefix = prefix;
	}

	@XmlElement
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
}
