package edu.asu.spring.quadriga.profile;

import java.util.List;

import edu.asu.spring.quadriga.jaxb.viaf.Item;
import edu.asu.spring.quadriga.jaxb.viaf.Channel;


public interface IViafManager {
	//public List<ViafReply.Items> search(String item, String startIndex);

	public List<ISearchResult> search(String item, String startIndex);
}
