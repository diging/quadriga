package edu.asu.spring.quadriga.profile;

import java.util.List;

import edu.asu.spring.quadriga.profile.impl.ViafItemReply;
import edu.asu.spring.quadriga.profile.impl.ViafReply;


public interface IViafManager {
	public List<ViafReply.Items> search(String item, String startIndex);
}
