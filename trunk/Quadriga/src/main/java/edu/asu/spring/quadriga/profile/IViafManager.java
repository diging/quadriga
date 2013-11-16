package edu.asu.spring.quadriga.profile;

import java.util.List;

import edu.asu.spring.quadriga.profile.impl.Item;
import edu.asu.spring.quadriga.profile.impl.Items;


public interface IViafManager {
	//public List<ViafReply.Items> search(String item, String startIndex);

	public List<Item> search(String item, String startIndex);
}
